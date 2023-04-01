package smevsign.cryptopro;

import com.objsys.asn1j.runtime.*;
import ru.CryptoPro.JCP.ASN.CryptographicMessageSyntax.*;
import ru.CryptoPro.JCP.ASN.PKIX1Explicit88.*;
import ru.CryptoPro.JCP.params.OID;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Signature;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;

/**
 *
 * jcp-2.0.40132-A\samples-sources.jar\CMS_samples\CMS.java : createCMSEx
 *
 * created 15.08.2007 11:42:12 by kunina
 *
 */

public class CMS {
    private static final String STR_CMS_OID_DATA = "1.2.840.113549.1.7.1";
    private static final String STR_CMS_OID_SIGNED = "1.2.840.113549.1.7.2";
    public static final String STR_CMS_OID_CONT_TYP_ATTR = "1.2.840.113549.1.9.3";
    public static final String STR_CMS_OID_DIGEST_ATTR = "1.2.840.113549.1.9.4";
    public static final String STR_CMS_OID_SIGN_TYM_ATTR = "1.2.840.113549.1.9.5";
    private final boolean detached;
    private final Certificate cert;
    private final PrivateKey privateKey;
    private final CryptoAlgorithm cryptoAlgorithm;

    public CMS(boolean detached, Certificate certificate, PrivateKey privateKey, CryptoAlgorithm cryptoAlgorithm) {
        this.detached = detached;
        this.cert = certificate;
        this.privateKey = privateKey;
        this.cryptoAlgorithm = cryptoAlgorithm;
    }

    public byte[] createCMS(byte[] buffer, byte[] sign, byte[] digest, boolean needSignedAttrs) throws Exception {
        final ContentInfo all = new ContentInfo();
        all.contentType = new Asn1ObjectIdentifier(new OID(STR_CMS_OID_SIGNED).value);

        final SignedData cms = new SignedData();
        all.content = cms;
        cms.version = new CMSVersion(1);

        // digest
        cms.digestAlgorithms = new DigestAlgorithmIdentifiers(1);
        final DigestAlgorithmIdentifier a = new DigestAlgorithmIdentifier(new OID(cryptoAlgorithm.digestAlgorithm.oid).value);

        a.parameters = new Asn1Null();
        cms.digestAlgorithms.elements[0] = a;

        if (this.detached) {
            cms.encapContentInfo = new EncapsulatedContentInfo(
                    new Asn1ObjectIdentifier(new OID(STR_CMS_OID_DATA).value),
                    null
            );
        } else {
            cms.encapContentInfo = new EncapsulatedContentInfo(
                    new Asn1ObjectIdentifier(new OID(STR_CMS_OID_DATA).value),
                    new Asn1OctetString(buffer)
            );
        }

        // certificate
        cms.certificates = new CertificateSet(1);
        final ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Certificate certificate =
                new ru.CryptoPro.JCP.ASN.PKIX1Explicit88.Certificate();
        final Asn1BerDecodeBuffer decodeBuffer =
                new Asn1BerDecodeBuffer(cert.getEncoded());
        certificate.decode(decodeBuffer);

        cms.certificates.elements = new CertificateChoices[1];
        cms.certificates.elements[0] = new CertificateChoices();
        cms.certificates.elements[0].set_certificate(certificate);

        // signer info
        cms.signerInfos = new SignerInfos(1);
        cms.signerInfos.elements[0] = new SignerInfo();
        cms.signerInfos.elements[0].version = new CMSVersion(1);
        cms.signerInfos.elements[0].sid = new SignerIdentifier();

        final byte[] encodedName = ((X509Certificate) cert).getIssuerX500Principal().getEncoded();
        final Asn1BerDecodeBuffer nameBuf = new Asn1BerDecodeBuffer(encodedName);
        final Name name = new Name();
        name.decode(nameBuf);

        final CertificateSerialNumber num = new CertificateSerialNumber(
                ((X509Certificate) cert).getSerialNumber()
        );
        cms.signerInfos.elements[0].sid.set_issuerAndSerialNumber(
                new IssuerAndSerialNumber(name, num));
        cms.signerInfos.elements[0].digestAlgorithm =
                new DigestAlgorithmIdentifier(new OID(cryptoAlgorithm.digestAlgorithm.oid).value);
        cms.signerInfos.elements[0].digestAlgorithm.parameters = new Asn1Null();
        cms.signerInfos.elements[0].signatureAlgorithm =
                new SignatureAlgorithmIdentifier(new OID(cryptoAlgorithm.signatureAlgorithm.oid).value);
        cms.signerInfos.elements[0].signatureAlgorithm.parameters = new Asn1Null();
        cms.signerInfos.elements[0].signature = new SignatureValue(sign);

        // signer info - signed attrs
        if (needSignedAttrs) {
            // методические рекомендации смэв: 6.3.1. Подписи в формате PKCS#7
            cms.signerInfos.elements[0].signedAttrs = getSignedAttrs(buffer, digest);
            cms.signerInfos.elements[0].signature = new SignatureValue(
                    signAttrs(cms.signerInfos.elements[0].signedAttrs)
            );
        }

        // encode
        final Asn1BerEncodeBuffer asnBuf = new Asn1BerEncodeBuffer();
        all.encode(asnBuf, true);
        return asnBuf.getMsgCopy();
    }

    /**
     *
     * jcp-2.0.40132-A\samples-sources.jar\CMS_samples\CMSSign.java : createHashCMSEx
     *
     */

    private SignedAttributes getSignedAttrs(byte[] data, byte[] digest) throws Exception {
        SignedAttributes signedAttrs = new SignedAttributes(3);

        // -content type
        signedAttrs.elements[0] = new Attribute(new OID(STR_CMS_OID_CONT_TYP_ATTR).value, new Attribute_values(1));
        signedAttrs.elements[0].values.elements[0] =  new Asn1ObjectIdentifier(new OID(STR_CMS_OID_DATA).value);

        // -time
        signedAttrs.elements[1] = new Attribute(new OID(STR_CMS_OID_SIGN_TYM_ATTR).value, new Attribute_values(1));
        final Time time = new Time();
        final Asn1UTCTime UTCTime = new Asn1UTCTime();
        UTCTime.setTime(Calendar.getInstance());
        time.set_utcTime(UTCTime);
        signedAttrs.elements[1].values.elements[0] = time.getElement();

        // -message digest
        signedAttrs.elements[2] = new Attribute(new OID(STR_CMS_OID_DIGEST_ATTR).value, new Attribute_values(1));
        //signedAttrs.elements[2].values.elements[0] = new Asn1OctetString(getDigest(data));
        signedAttrs.elements[2].values.elements[0] = new Asn1OctetString(digest);

        return signedAttrs;
    }

    private byte[] getDigest(byte[] data) throws IOException, NoSuchProviderException, NoSuchAlgorithmException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(data);
        final java.security.MessageDigest digest = java.security.MessageDigest.getInstance(cryptoAlgorithm.digestAlgorithm.name, "JCP");
        final DigestInputStream digestStream = new DigestInputStream(stream, digest);
        while (digestStream.available() != 0) digestStream.read();
        return digest.digest();
    }

    private byte[] signAttrs(SignedAttributes signedAttributes) throws Exception {
        if (this.privateKey == null) return null;

        Asn1BerEncodeBuffer encBufSignedAttr = new Asn1BerEncodeBuffer();
        signedAttributes.encode(encBufSignedAttr);
        final byte[] hSign = encBufSignedAttr.getMsgCopy();

        final Signature signature = Signature.getInstance(cryptoAlgorithm.signatureAlgorithm.name, "JCP");

        signature.initSign(this.privateKey);
        signature.update(hSign);
        return signature.sign();
    }

}
