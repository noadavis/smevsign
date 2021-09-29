package smevsign.smev.signature;


import org.apache.commons.codec.binary.Base64;
import smevsign.cryptopro.CryptoAlgorithm;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "SignedInfo", namespace = SignedInfo.XMLNS)
@XmlAccessorType(XmlAccessType.FIELD)
public class SignedInfo {
    public static final String XMLNS = "http://www.w3.org/2000/09/xmldsig#";
    public static final String CANONICALIZATION_METHOD = "http://www.w3.org/2001/10/xml-exc-c14n#";
    public static final String ADDITIONAL_NORMALIZE_METHOD = "urn://smev-gov-ru/xmldsig/transform";

    public void setCanonicalizationMethod(String canonicalizationAlgorithm) {
        if (canonicalizationAlgorithm == null) {
            this.canonicalizationMethod.algorithm = CANONICALIZATION_METHOD;
        } else {
            this.canonicalizationMethod.algorithm = canonicalizationAlgorithm;
        }
    }

    public void setSignatureMethod(String signatureAlgorithm) {
        if (signatureAlgorithm == null) {
            this.signatureMethod.algorithm = CryptoAlgorithm.GOST_3411_2012_256.signatureMethod;
        } else {
            this.signatureMethod.algorithm = signatureAlgorithm;
        }
    }

    public void setReference(String referenceId, byte[] digestValue, String digestMethod) {
        this.reference.uri = String.format("#%s", referenceId);
        this.reference.transforms.add(new Transform(CANONICALIZATION_METHOD));
        this.reference.transforms.add(new Transform(ADDITIONAL_NORMALIZE_METHOD));
        this.reference.digestMethod.algorithm = digestMethod;
        this.reference.setDigestValue(digestValue);
    }

    public String getString() {
        try {
            JAXBContext context = JAXBContext.newInstance(SignedInfo.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal(this, sw);
            return sw.toString();
        } catch (JAXBException e) {
            System.out.printf("SignedInfo convert Exception: %s", e);
        }
        return null;
    }

    @XmlElement(name = "CanonicalizationMethod", namespace = XMLNS)
    public CanonicalizationMethod canonicalizationMethod = new CanonicalizationMethod();

    @XmlElement(name = "SignatureMethod", namespace = XMLNS)
    public SignatureMethod signatureMethod = new SignatureMethod();

    @XmlElement(name = "Reference", namespace = XMLNS)
    public Reference reference = new Reference();

    public SignedInfo() {}

    public SignedInfo(String signatureMethodAlgorithm) {
        if ("".equals(signatureMethodAlgorithm)) {
            setSignatureMethod(null);
        } else {
            setSignatureMethod(signatureMethodAlgorithm);
        }
        setCanonicalizationMethod(null);
    }

    /** INNER CLASSES **/

    static class CanonicalizationMethod {
        @XmlAttribute(name = "Algorithm")
        public String algorithm;
    }

    static class SignatureMethod {
        @XmlAttribute(name = "Algorithm")
        public String algorithm;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    static class Reference {
        @XmlAttribute(name = "URI")
        public String uri;

        @XmlElementWrapper(name = "Transforms", namespace = XMLNS)
        @XmlElement(name = "Transform", namespace = XMLNS)
        public List<Transform> transforms = new ArrayList<Transform>();

        @XmlElement(name = "DigestMethod", namespace = XMLNS)
        public DigestMethod digestMethod = new DigestMethod();

        @XmlElement(name = "DigestValue", namespace = XMLNS)
        private String digestValue = "";

        public byte[] getDigestValue() {
            return Base64.decodeBase64(digestValue);
        }

        public void setDigestValue(byte[] digestValue) {
            this.digestValue = Base64.encodeBase64String(digestValue);
        }
    }

    static class Transform {
        @XmlAttribute(name = "Algorithm")
        public String algorithm;

        public Transform() { }

        public Transform(String algorithm) {
            this.algorithm = algorithm;
        }
    }

    static class DigestMethod {
        @XmlAttribute(name = "Algorithm")
        public String algorithm;
    }
}