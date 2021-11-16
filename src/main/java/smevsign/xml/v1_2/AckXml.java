package smevsign.xml.v1_2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smevsign.cryptopro.SignValues;
import smevsign.cryptopro.SignXml;
import smevsign.smev.Utils;
import smevsign.smev.signature.Signature;
import smevsign.smev.v1_2.AckRequest;
import smevsign.smev.v1_2.AckTargetMessage;
import smevsign.smev.v1_2.XMLDSigSignatureType;
import smevsign.support.ContainerConfig;
import smevsign.support.Settings;
import smevsign.xml.AbstractXmlBuilder;
import smevsign.xml.Smev3Ack;

public class AckXml extends AbstractXmlBuilder implements Smev3Ack {
    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);
    private final Settings settings;
    private SignValues signValues = new SignValues("AckTargetMessageId");
    private final boolean accepted;
    private final boolean debug;
    private final ContainerConfig container;


     public AckXml(boolean accepted, Settings jsonInputSettings, ContainerConfig container, boolean debug) {
        this.settings = jsonInputSettings;
        this.accepted = accepted;
        this.container = container;
        this.debug = debug;

        createAckXml();
    }

    public void createAckXml() {
        this.signValues.setElementNs(
                "AckTargetMessage",
                "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2"
        );
        if ("AckRequest".equals(this.settings.getDataType())) {
            createRequest();
        } else if ("AckResponse".equals(this.settings.getDataType())) {
            createResponse();
        } else {
            setError("[CREATE ACK] unknown data type", log);
        }
    }

    private void createRequest() {
        String ackUuid = this.settings.getUuid();
        if (ackUuid == null) {
            setError("[CREATE ACK REQUEST] ackUuid is null", log);
            return;
        }

        AckRequest ackReq = new AckRequest();
        AckTargetMessage target = new AckTargetMessage();
        target.setAccepted(this.accepted);
        target.setId(this.signValues.getReferenceId());
        target.setValue(ackUuid);
        ackReq.setAckTargetMessage(target);

        XMLDSigSignatureType dSign = new XMLDSigSignatureType();
        ackReq.setCallerInformationSystemSignature(dSign);

        SignXml signXml = new SignXml(this.debug, this.container);
        Signature signature = signXml.sign(ackReq, this.signValues);
        if (signature != null) {
            dSign.setAny(Utils.getElementFromClass(signature));
            setXml(Utils.convertJAXBObjectToXml(ackReq, "smevsign.smev.v1_2.AckRequest", null));
        } else {
            setError("[CREATE ACK REQUEST] sign not created", log);
            setXml(null);
        }
    }

    private void createResponse() {
        String xmlScheme = this.settings.getXmlScheme();
        if (xmlScheme == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header/><soap:Body>");
        sb.append("<ns2:AckResponse xmlns=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2\"");
        sb.append(" xmlns:ns2=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.2\"");
        sb.append(" xmlns:ns3=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.2\"/>");
        sb.append("</soap:Body></soap:Envelope>");
        setXml(sb.toString());
    }

}
