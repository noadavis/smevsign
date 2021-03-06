package smevsign.cryptopro;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import smevsign.smev.Utils;
import smevsign.smev.XmlNormalizer;
import smevsign.smev.signature.Signature;
import smevsign.smev.signature.SignedInfo;
import smevsign.support.ContainerConfig;

import jakarta.xml.bind.JAXB;
import smevsign.support.DigestValue;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;
import java.security.NoSuchAlgorithmException;

public class SignXml {
    
    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);
    private final boolean debug;
    private final ContainerConfig container;

    public SignXml(boolean debug, ContainerConfig container) {
        this.debug = debug;
        this.container = container;
    }

    private byte[] normalizeRequest(Object serviceRequest, XmlNormalizer normalizer, String namespaceUri, String localName) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            JAXB.marshal(serviceRequest, new DOMResult(document));
            NodeList nl = null;
            if (namespaceUri != null) {
                nl = document.getDocumentElement().getElementsByTagNameNS(namespaceUri, localName);
            } else {
                nl = document.getDocumentElement().getElementsByTagName(localName);
            }
            if (nl != null && nl.getLength() > 0) {
                String nodeToSign = Utils.getStringFromNode(nl.item(0));
                return normalizer.makeSmevTransform(nodeToSign);
            }
        } catch (ParserConfigurationException e) {
            log.error("normalizeRequest exception: " + e.getMessage());
        }
        return null;
    }

    private String getRefId(String node) {
        Element element = Utils.getElementFromXmlString(node);
        if (element == null) {
            return null;
        }
        String refId = element.getAttribute("Id");
        if (refId == null || refId.isEmpty()) {
            return null;
        }
        return refId;
    }

    public DigestValue getDigest(String node, String algorithm) {
        CryptoAlgorithm cryptoAlgorithm = null;
        try {
            cryptoAlgorithm = CryptoAlgorithm.getCryptoAlgorithm(algorithm);
        } catch (NoSuchAlgorithmException e) {
            //
        }
        if (cryptoAlgorithm == null) {
            log.error("[GETDIGEST] cryptoAlgorithm is null");
            return null;
        }
        XmlNormalizer transform = new XmlNormalizer(this.debug);
        byte[] normalizedTargetNode = transform.makeSmevTransform(node);

        byte[] nodeDigest = nodeDigest(normalizedTargetNode, cryptoAlgorithm.digestAlgorithm.name, "DIGEST");

        String refId = getRefId(node);
        if (refId == null) {
            System.out.println("[ERROR] PersonalSignature: attribute Id not found");
            return null;
        }
        SignedInfo signedInfo = new SignedInfo(cryptoAlgorithm.signatureMethod);
        signedInfo.setReference(refId, nodeDigest, cryptoAlgorithm.digestMethod);

        Node signedInfoNode = Utils.getElementFromClass(signedInfo);
        byte[] normalizedSignedInfoNode = transform.makeC14nTransform(signedInfoNode);
        if (normalizedSignedInfoNode == null) {
            log.error("[GETDIGEST] normalizedSignedInfoNode is null");
            return null;
        }
        if (this.debug) {
            log.info(new String(normalizedSignedInfoNode));
        }
        byte[] signedInfoDigest = nodeDigest(normalizedSignedInfoNode, cryptoAlgorithm.digestAlgorithm.name, "DIGEST");

        return new DigestValue(algorithm, nodeDigest, signedInfoDigest);
    }

    private byte[] nodeDigest(byte[] normalizedTargetNode, String digestAlgorithm, String operationType) {
        if (normalizedTargetNode == null) {
            log.error(String.format("[%s] normalizedTargetNode is null", operationType));
            return null;
        }
        if (this.debug) {
            log.info(new String(normalizedTargetNode));
        }
        //calculate digest
        Digest digest = new Digest();
        byte[] digestValue = digest.digest(normalizedTargetNode, digestAlgorithm);
        if (digestValue == null) {
            log.error(String.format("[%s] digestValue is null", operationType));
            return null;
        }
        if (this.debug) {
            log.info("digest: " + Base64.encodeBase64String(digestValue));
        }
        return digestValue;
    }

    public Signature sign(Object serviceRequest, SignValues signValues) {
        //get container content
        ContainerService cs = new ContainerService(container.alias, container.password);
        if (!cs.isCertificateDateValid()) {
            log.error(String.format("[SIGN][%s] certificate date not valid", container.alias));
            return null;
        }

        //<ds:Signature>
        Signature signature = new Signature();
        //<ds:SignedInfo>
        SignedInfo signedInfo = new SignedInfo(cs.getSignatureMethod());
        signature.setSignedInfo(signedInfo);

        //get xml normalizer
        XmlNormalizer transform = new XmlNormalizer(this.debug);

        //transform node for digest
        byte[] normalizedTargetNode = normalizeRequest(
                    serviceRequest, transform, signValues.getNamespace(), signValues.getElement()
        );
        byte[] digestValue = nodeDigest(normalizedTargetNode, cs.getCryptoAlgorithm().digestAlgorithm.name, "SIGN");
        if (digestValue == null) {
            return null;
        }

        signedInfo.setReference(signValues.getReferenceId(), digestValue, cs.getDigestMethod());

        //prepare <ds:SignedInfo>
        Node signedInfoNode = Utils.getElementFromClass(signedInfo);
        byte[] normalizedSignedInfoNode = transform.makeC14nTransform(signedInfoNode);
        if (normalizedSignedInfoNode == null) {
            log.error("[SIGN] normalizedSignedInfoNode is null");
            return null;
        }
        if (this.debug) {
            log.info(new String(normalizedSignedInfoNode));
        }

        //sign <ds:SignedInfo>
        byte[] signedSignedInfo = cs.sign(normalizedSignedInfoNode);
        signature.setSignatureValue(signedSignedInfo);
        signature.setCertificate(cs.getCertificate());
        if (this.debug) {
            log.info(Utils.convertSignatureObjectToXml(signature));
        }
        return signature;
    }
}
