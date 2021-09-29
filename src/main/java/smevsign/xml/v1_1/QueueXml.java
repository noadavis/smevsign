package smevsign.xml.v1_1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smevsign.cryptopro.SignValues;
import smevsign.cryptopro.SignXml;
import smevsign.smev.Utils;
import smevsign.smev.signature.Signature;
import smevsign.smev.v1_1.GetRequestRequest;
import smevsign.smev.v1_1.GetResponseRequest;
import smevsign.smev.v1_1.MessageTypeSelector;
import smevsign.smev.v1_1.XMLDSigSignatureType;
import smevsign.support.ContainerConfig;
import smevsign.support.Settings;
import smevsign.xml.AbstractXmlBuilder;

public class QueueXml extends AbstractXmlBuilder {
    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);
    private final Settings settings;
    private SignValues signValues = new SignValues("MessageIdentifierId");
    private final boolean debug;
    private final ContainerConfig container;

    public QueueXml(Settings jsonInputSettings, ContainerConfig container, boolean debug) {
        this.settings = jsonInputSettings;
        this.container = container;
        this.debug = debug;

        createQueueXml();
    }

    /*
    SendRequestRequest -> {SendRequestResponse} : [Отправка запроса] [Ф] {ТЕХ ОТВЕТ}
    [GetResponseRequest] -> GetResponseResponse : [Опрос очереди ответов] [Ф]

    [GetRequestRequest] -> [GetRequestResponse] : [Опрос очереди запросов] [Р]
    SendResponseRequest -> {SendResponseResponse} : [Отправка ответа] [Р] {ТЕХ ОТВЕТ}
    */
    public void createQueueXml() {
        if ("GetRequestRequest".equals(this.settings.getDataType())) {
            createGetRequestRequest();
        } else if ("GetResponseRequest".equals(this.settings.getDataType())) {
            createGetResponseRequest();
        } else if ("GetRequestResponse".equals(this.settings.getDataType())) {
            createGetRequestResponse();
        } else if ("GetResponseResponse".equals(this.settings.getDataType())) {
            createGetResponseResponse();
        } else {
            System.out.println("[CREATE QUEUE] unknown data type");
        }
    }

    //ОПРОС ОЧЕРЕДИ ЗАПРОСОВ [Р]
    private void createGetRequestRequest() {
        this.signValues.setElementNs(
                "MessageTypeSelector",
                "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1"
        );

        GetRequestRequest getReq = new GetRequestRequest();
        MessageTypeSelector typeSelector = new MessageTypeSelector();
        typeSelector.setId(this.signValues.getReferenceId());
        typeSelector.setTimestamp(Utils.getXMLGregorianCalendarNow());
        getReq.setMessageTypeSelector(typeSelector);

        XMLDSigSignatureType dSign = new XMLDSigSignatureType();
        getReq.setCallerInformationSystemSignature(dSign);

        SignXml signXml = new SignXml(this.debug, this.container);
        Signature signature = signXml.sign(getReq, this.signValues);
        if (signature != null) {
            dSign.setAny(Utils.getElementFromClass(signature));
            setXml(Utils.convertJAXBObjectToXml(getReq, "smevsign.smev.v1_1.GetRequestRequest", null));
        } else {
            System.out.println("[QUEUE GETREQUESTREQUEST] sign not created");
            setXml(null);
        }
    }

    //ОПРОС ОЧЕРЕДИ ОТВЕТОВ [Ф]
    private void createGetResponseRequest() {
        this.signValues.setElementNs(
                "MessageTypeSelector",
                "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1"
        );

        GetResponseRequest getRes = new GetResponseRequest();
        MessageTypeSelector typeSelector = new MessageTypeSelector();
        typeSelector.setId(this.signValues.getReferenceId());
        typeSelector.setTimestamp(Utils.getXMLGregorianCalendarNow());
        getRes.setMessageTypeSelector(typeSelector);

        XMLDSigSignatureType dSign = new XMLDSigSignatureType();
        getRes.setCallerInformationSystemSignature(dSign);

        SignXml signXml = new SignXml(this.debug, this.container);
        Signature signature = signXml.sign(getRes, this.signValues);
        if (signature != null) {
            dSign.setAny(Utils.getElementFromClass(signature));
            setXml(Utils.convertJAXBObjectToXml(getRes, "smevsign.smev.v1_1.GetResponseRequest", null));
        } else {
            System.out.println("[QUEUE GETRESPONSE] sign not created");
            setXml(null);
        }
    }

    //ОТВЕТ НА ОПРОС ПУСТОЙ ОЧЕРЕДИ ЗАПРОСОВ [ЭМУЛЯТОР]
    private void createGetRequestResponse() {
        String xmlScheme = this.settings.getXmlScheme();
        if (xmlScheme == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header/><soap:Body>");
        sb.append("<st1:GetRequestResponse xmlns:sb1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"");
        sb.append(" xmlns:sf1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\"");
        sb.append(" xmlns:st1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"/>");
        sb.append("</soap:Body></soap:Envelope>");
        setXml(sb.toString());
    }

    //ОТВЕТ НА ОПРОС ПУСТОЙ ОЧЕРЕДИ ОТВЕТОВ [ЭМУЛЯТОР]
    private void createGetResponseResponse() {
        String xmlScheme = this.settings.getXmlScheme();
        if (xmlScheme == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header/><soap:Body>");
        sb.append("<st1:GetResponseResponse xmlns:sb1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1\"");
        sb.append(" xmlns:sf1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/faults/1.1\"");
        sb.append(" xmlns:st1=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1\"/>");
        sb.append("</soap:Body></soap:Envelope>");
        setXml(sb.toString());
    }
}
