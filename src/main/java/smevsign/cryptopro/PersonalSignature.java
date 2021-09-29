package smevsign.cryptopro;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Element;
import smevsign.smev.Utils;
import smevsign.smev.signature.Signature;
import smevsign.smev.signature.SignedInfo;
import smevsign.smev.v1_1.XMLDSigSignatureType;
import smevsign.support.PersonalSign;

import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class PersonalSignature {
    public XMLDSigSignatureType getPersonSignature(PersonalSign personalSign, Element any){
        Signature personalSignature = new Signature();
        CryptoAlgorithm algorithm = null;
        try {
            algorithm = CryptoAlgorithm.getCryptoAlgorithm(personalSign.algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (algorithm != null) {
            SignedInfo signedInfo = new SignedInfo(algorithm.signatureMethod);
            String refId = any.getAttribute("Id");
            if (refId == null || refId.isEmpty()) {
                System.out.println("[ERROR] PersonalSignature: attribute Id not found");
                return null;
            }
            signedInfo.setReference(
                    refId,
                    Base64.decodeBase64(personalSign.digestValue),
                    algorithm.digestMethod
            );
            personalSignature.setSignedInfo(signedInfo);
            personalSignature.setSignatureValueByString(personalSign.signatureValue);
            personalSignature.setCertificate(this.getOpenCertInfo(personalSign.certificate));

            XMLDSigSignatureType dSig = new XMLDSigSignatureType();
            dSig.setAny(Utils.getElementFromClass(personalSignature));
            return dSig;
        } else {
            return null;
        }
    }

    private X509Certificate getOpenCertInfo(String base64PublicKey) {
        byte[] publicKeyBytes = Base64.decodeBase64(base64PublicKey);
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(publicKeyBytes));
            return certificate;
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

}
