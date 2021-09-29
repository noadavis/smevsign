package smevsign.smev.signature;

import org.apache.commons.codec.binary.Base64;
import ru.CryptoPro.reprov.array.DerOutputStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


@XmlRootElement(name = "Signature", namespace = Signature.XMLNS)
@XmlAccessorType(XmlAccessType.FIELD)
public class Signature {
    public static final String XMLNS = "http://www.w3.org/2000/09/xmldsig#";

    public void setSignedInfo(SignedInfo signedInfo) {
        this.signedInfo = signedInfo;
    }

    public void setSignatureValue(byte[] signatureValue) {
        this.signatureValue = Base64.encodeBase64String(signatureValue);
    }

    public void setCertificate(X509Certificate certificate) {
        keyInfo.x509Data.setCertificate(certificate);
        keyInfo.x509Data.setSubjectName(certificate.getSubjectDN().toString());
    }

    @XmlElement(name = "SignedInfo", namespace = XMLNS)
    private SignedInfo signedInfo;

    @XmlElement(name = "SignatureValue", namespace = XMLNS)
    private String signatureValue;

    @XmlElement(name = "KeyInfo", namespace = XMLNS)
    private KeyInfo keyInfo = new KeyInfo();

    static class KeyInfo {
        @XmlElement(name = "X509Data", namespace = XMLNS)
        public X509Data x509Data = new X509Data();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    static class X509Data {
        @XmlElement(name = "X509Certificate", namespace = XMLNS)
        private String x509Certificate = "";
        @SuppressWarnings("unused")
        @XmlElement(name = "X509SubjectName", namespace = XMLNS)
        private String x509SubjectName = "";

        public void setSubjectName(String subjectName) {
            this.x509SubjectName = subjectName;
        }

        @SuppressWarnings("Duplicates")
        public void setCertificate(X509Certificate certificate) {
            DerOutputStream derEncoder;
            try {
                derEncoder = new DerOutputStream();
                derEncoder.write(certificate.getEncoded());
            } catch (CertificateEncodingException | IOException e) {
                throw new RuntimeException(e);
            }
            this.x509Certificate = Base64.encodeBase64String(derEncoder.toByteArray());
        }

        @SuppressWarnings("Duplicates")
        public X509Certificate getCertificate() {
            try {
                byte[] derBytes = Base64.decodeBase64(x509Certificate);
                ByteArrayInputStream is = new ByteArrayInputStream(derBytes);
                return (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(is);
            } catch (CertificateException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
