package smevsign.cryptopro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.CryptoPro.JCP.JCP;

import java.io.*;
import java.security.*;
import java.security.cert.*;

public class ContainerService {
    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);
    private boolean CertificateDateValid = false;
    private X509Certificate certificate = null;

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    private PrivateKey privateKey = null;
    private CryptoAlgorithm cryptoAlgorithm = null;
    private final String alias;
    private final char[] password;

    public ContainerService(String containerAlias, String containerPassword) {
        if (Security.getProvider("JCP") == null) {
            Security.addProvider(new JCP());
        }
        this.alias = containerAlias;
        this.password = containerPassword.toCharArray();
        getContainerKeys();
    }
    public CryptoAlgorithm getCryptoAlgorithm() {
        return cryptoAlgorithm;
    }
    public boolean isCertificateDateValid() { return CertificateDateValid; }
    public String getSignatureMethod() { return cryptoAlgorithm.signatureMethod; }
    public String getDigestMethod() { return cryptoAlgorithm.digestMethod; }
    public X509Certificate getCertificate() { return certificate; }

    public byte[] sign(byte[] data) {
        if (CertificateDateValid) {
            try {
                java.security.Signature sign = java.security.Signature.getInstance(cryptoAlgorithm.signatureAlgorithm.name);
                sign.initSign(this.privateKey);
                sign.update(data);
                return sign.sign();
            } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
                log.error("", e);
            }
        }
        return null;
    }


    public void getContainerKeys() {
        try {
            KeyStore keyStore = KeyStore.getInstance(JCP.HD_STORE_NAME, "JCP");
            keyStore.load(null, null);
            if (keyStore.containsAlias(this.alias)) {
                this.privateKey = (PrivateKey) keyStore.getKey(this.alias, this.password);
                this.certificate = ((X509Certificate) keyStore.getCertificate(this.alias));
                try {
                    this.certificate.checkValidity();
                    this.CertificateDateValid = true;
                    log.info("Загружен действующий до " + this.certificate.getNotAfter() + " сертификат " + this.certificate.getSubjectDN().getName());
                } catch (CertificateExpiredException e) {
                    log.error("Закончилось время действия для сертификата " + this.certificate.getSubjectDN().getName());
                    this.CertificateDateValid = false;
                } catch (CertificateNotYetValidException e) {
                    log.error("Не началось время действия для сертификата " + this.certificate.getSubjectDN().getName());
                    this.CertificateDateValid = false;
                }
                if (this.CertificateDateValid) {
                    this.cryptoAlgorithm = CryptoAlgorithm.getCryptoAlgorithm(this.certificate.getPublicKey().getAlgorithm());
                }
            }
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | NoSuchProviderException e) {
            if (e.getMessage().contains("ASN.1 decode error")) {
                log.error("[ASN.1 decode error] может означать проблему контейнера с ЭЦП, проверьте присутствие и размер файлов в /var/opt/cprocsp/keys/");
            }
            log.error("", e);
        }
    }

    private boolean verify(byte[] data, byte[] signature) throws Exception {
//        PublicKey publicKey = certificate.getPublicKey();
//        if (publicKey != null && CertificateDateValid) {
//            java.security.Signature sign = java.security.Signature.getInstance(cryptoAlgorithm.signatureAlgorithm.name);
//            sign.initVerify(publicKey);
//            sign.update(data);
//            return sign.verify(signature);
//        }
//        log.error("try to verify but PublicKey is not valid");
        return false;
    }

}
