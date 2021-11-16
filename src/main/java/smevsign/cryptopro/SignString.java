package smevsign.cryptopro;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smevsign.support.ContainerConfig;
import smevsign.support.Settings;
import smevsign.xml.AbstractXmlBuilder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.Signature;


public class SignString extends AbstractXmlBuilder {
    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);
    private final Settings settings;
    private final ContainerConfig container;
    private ContainerService cs = null;
    private CMS cms = null;

    public SignString(Settings jsonInputSettings, ContainerConfig container) {
        this.settings = jsonInputSettings;
        this.container = container;

        SignPrepare();

        this.setValue(signString());
    }

    private String signString() {
        try {
            byte[] data = settings.getData().getBytes(StandardCharsets.UTF_8);
            final PrivateKey key = this.cs.getPrivateKey();

            final Signature signature = Signature.getInstance(this.cs.getCryptoAlgorithm().signatureAlgorithm.name, "JCP");
            signature.initSign(key);
            signature.update(data);
            final byte[] sign = signature.sign();

            byte[] pkcs7 = this.cms.createCMS(
                    data,
                    sign,
                    this.cs.getCryptoAlgorithm().digestAlgorithm.oid,
                    this.cs.getCryptoAlgorithm().signatureAlgorithm.oid
            );

            if (settings.options.urlSafe) return Base64.encodeBase64URLSafeString(pkcs7);
            return Base64.encodeBase64String(pkcs7);

        } catch (Exception e) {
            setError(e.getMessage(), log);
        }
        return null;
    }

    private void SignPrepare() {
        this.cs = new ContainerService(container.alias, container.password);
        if (!this.cs.isCertificateDateValid()) {
            setError(String.format("[SIGN][%s] certificate date not valid", container.alias), log);
            return;
        }
        this.cms = new CMS(false, cs.getCertificate());
    }

}
