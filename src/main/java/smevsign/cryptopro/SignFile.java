package smevsign.cryptopro;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smevsign.support.AttachmentInfo;
import smevsign.support.ContainerConfig;
import smevsign.support.Settings;
import smevsign.xml.AbstractXmlBuilder;
import ru.CryptoPro.JCP.tools.Array;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class SignFile extends AbstractXmlBuilder {
    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);
    private final Settings settings;
    private final boolean debug;
    private final ContainerConfig container;
    private ContainerService cs = null;
    private CMS cms = null;
    private Digest digest = null;

    public SignFile(Settings jsonInputSettings, ContainerConfig container, boolean debug) {
        this.settings = jsonInputSettings;
        this.container = container;
        this.debug = debug;

        SignPrepare(this.settings.options.signatureDetached);

        GenerateSig();
    }

    public SignFile(ContainerConfig container, boolean debug) {
        this.settings = null;
        this.container = container;
        this.debug = debug;
        this.digest = new Digest();

        SignPrepare(true);
    }

    public void GeneratePkcs7(AttachmentInfo file, File f) {
        byte[] attachmentDigest = digest.fileDigest(f, cs.getCryptoAlgorithm().digestAlgorithm.name);
        file.digest = Base64.encodeBase64String(attachmentDigest);
        file.pkcs7 = signFile(f, false);
    }

    private void GenerateSig() {
        List<AttachmentInfo> signedFiles = new ArrayList<AttachmentInfo>();
        List<AttachmentInfo> files = settings.getFiles();
        boolean signError = false;

        if (files.size() > 0) {
            try {
                for (AttachmentInfo file : files) {
                    File f = new File(String.format("%s%s", file.filePath, file.fileName));
                    if (f.exists()) {
                        byte[] pkcs7 = signFile(f, true);
                        if (pkcs7 != null) {
                            Array.writeFile(String.format("%s.sig", f.getPath()), pkcs7);
                            signedFiles.add(new AttachmentInfo(null, file.filePath, String.format("%s.sig", file.fileName)));
                            if (settings.options.signatureDetached) {
                                signedFiles.add(new AttachmentInfo(null, file.filePath, file.fileName));
                            }
                        } else {
                            signError = true;
                            break;
                        }
                    } else {
                        setError(String.format("file not exist: %s", file.fileName), log);
                    }
                }
            } catch (IOException e) {
                setError(e.getMessage(), log);
            }
        }

        if (!signError) {
            try {
                zipSignedFiles(signedFiles);
            } catch (IOException e) {
                setError(e.getMessage(), log);
            }
            if (!isError()) {
                setValue("signed");
            }
        }
    }

    private byte[] signFile(File f, boolean writeSig) {
        try {
            final byte[] data = Array.readFile(f.getPath());
            final PrivateKey key = this.cs.getPrivateKey();

            final Signature signature = Signature.getInstance(this.cs.getCryptoAlgorithm().signatureAlgorithm.name, "JCP");
            signature.initSign(key);
            signature.update(data);
            final byte[] sign = signature.sign();

            return this.cms.createCMS(
                    data,
                    sign,
                    this.cs.getCryptoAlgorithm().digestAlgorithm.oid,
                    this.cs.getCryptoAlgorithm().signatureAlgorithm.oid
            );

        } catch (Exception e) {
            setError(e.getMessage(), log);
        }
        return null;
    }

    private void zipSignedFiles(List<AttachmentInfo> signedFiles) throws IOException {
        if (signedFiles.size() == 0 || settings.options.zipName == null) {
            return;
        }
        FileOutputStream fos = new FileOutputStream(String.format("%s%s", signedFiles.get(0).filePath, settings.options.zipName));
        if (debug) {
            log.info(String.format("%s%s", signedFiles.get(0).filePath, settings.options.zipName));
        }
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (AttachmentInfo signedFile : signedFiles) {
            File fileToZip = new File(String.format("%s%s", signedFile.filePath, signedFile.fileName));
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[10240];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }

    private void SignPrepare(boolean signatureDetached) {
        this.cs = new ContainerService(container.alias, container.password);
        if (!this.cs.isCertificateDateValid()) {
            setError(String.format("[SIGN][%s] certificate date not valid", container.alias), log);
            return;
        }
        this.cms = new CMS(signatureDetached, cs.getCertificate());
    }

}
