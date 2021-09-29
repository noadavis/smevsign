package smevsign.cryptopro;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import smevsign.smev.Utils;
import smevsign.smev.XmlNormalizer;
import smevsign.smev.signature.Signature;
import smevsign.smev.signature.SignedInfo;
import smevsign.support.ContainerConfig;

import jakarta.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;

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
        XmlNormalizer transform = new XmlNormalizer();

        //transform node for digest
        byte[] normalizedTargetNode = normalizeRequest(
                    serviceRequest, transform, signValues.getNamespace(), signValues.getElement()
        );
        if (normalizedTargetNode == null) {
            log.error("[SIGN] normalizedTargetNode is null");
            return null;
        }
        if (this.debug) {
            log.info(new String(normalizedTargetNode));
        }

        //calculate digest
        Digest digest = new Digest();
        byte[] digestValue = digest.digest(normalizedTargetNode, cs.getCryptoAlgorithm().digestAlgorithm.name);
        if (digestValue == null) {
            log.error("[SIGN] digestValue is null");
            return null;
        }
        if (this.debug) {
            log.info("digest: " + Base64.encodeBase64String(digestValue));
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
