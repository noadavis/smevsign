package smevsign.cryptopro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    public SignFile(Settings jsonInputSettings, ContainerConfig container, boolean debug) {
        this.settings = jsonInputSettings;
        this.container = container;
        this.debug = debug;

        SignFiles();
    }

    private void SignFiles() {
        log.info(settings.options.path);
        if ((settings.options.path != null) && (!"".equals(settings.options.path))) {

            ContainerService cs = new ContainerService(container.alias, container.password);
            if (!cs.isCertificateDateValid()) {
                setError(String.format("[SIGN][%s] certificate date not valid", container.alias), log);
                return;
            }
            CMS cms = new CMS(settings.options.signature_detached, cs.getCertificate());

            List<String> signedFiles = new ArrayList<String>();
            boolean signError = false;

            if (settings.options.files.size() > 0) {
                log.info("work with files in array");
                for (String fn : settings.options.files) {
                    File f = new File(String.format("%s%s", settings.options.path, fn));
                    if (f.exists()) {
                        String signedFile = signFile(cs, cms, f);
                        if (signedFile != null) {
                            signedFiles.add(signedFile);
                            if (settings.options.signature_detached) {
                                signedFiles.add(f.getName());
                            }
                        } else {
                            signError = true;
                            break;
                        }
                    }else {
                        setError("file not exist", log);
                    }
                }
            } else {
                log.info("work with all files in path");
                File folder = new File(settings.options.path);
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File f : files) {
                        String signedFile = signFile(cs, cms, f);
                        if (signedFile != null) {
                            signedFiles.add(signedFile);
                            if (settings.options.signature_detached) {
                                signedFiles.add(f.getName());
                            }
                        } else {
                            signError = true;
                            break;
                        }
                    }
                } else {
                    setError("path is empty", log);
                }
            }

            if (!signError) {
                if (signedFiles.size() > 0 && settings.options.zipName != null) {
                    try {
                        zipSignedFiles(signedFiles, settings.options.path);
                    } catch (IOException e) {
                        setError(e.getMessage(), log);
                    }
                }
                if (!getError()) {
                    setXml("signed");
                }
            }

        } else {
            setError("path is empty", log);
        }
    }



    private String signFile(ContainerService cs, CMS cms, File f) {
        try {
            final byte[] data = Array.readFile(f.getPath());
            final PrivateKey key = cs.getPrivateKey();

            final Signature signature = Signature.getInstance(cs.getCryptoAlgorithm().signatureAlgorithm.name, "JCP");
            signature.initSign(key);
            signature.update(data);
            final byte[] sign = signature.sign();

            byte[] fileSignature = cms.createCMS(
                    data,
                    sign,
                    cs.getCryptoAlgorithm().digestAlgorithm.oid,
                    cs.getCryptoAlgorithm().signatureAlgorithm.oid
            );

            if (fileSignature != null) {
                Array.writeFile(String.format("%s.sig", f.getPath()), fileSignature);
                return String.format("%s.sig", f.getName());
            }
        } catch (Exception e) {
            setError(e.getMessage(), log);
        }
        return null;
    }


    private void zipSignedFiles(List<String> signedFiles, String path) throws IOException {
        FileOutputStream fos = new FileOutputStream(String.format("%s%s", path, settings.options.zipName));
        //log.info(String.format("%s%s", path, settings.options.zipName));
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String signedFile : signedFiles) {
            //log.info(String.format("%s%s", path, signedFile));
            if (!signedFile.equals(settings.options.zipName)) {
                File fileToZip = new File(String.format("%s%s", path, signedFile));
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
        }
        zipOut.close();
        fos.close();
    }


}
