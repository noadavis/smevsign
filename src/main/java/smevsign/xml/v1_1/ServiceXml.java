package smevsign.xml.v1_1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.Tika;
import org.w3c.dom.Element;
import smevsign.cryptopro.SignFile;
import smevsign.cryptopro.SignValues;
import smevsign.cryptopro.SignXml;
import smevsign.smev.Utils;
import smevsign.smev.signature.Signature;
import smevsign.smev.v1_1.*;
import smevsign.support.AttachmentInfo;
import smevsign.support.ContainerConfig;
import smevsign.support.Settings;
import smevsign.xml.AbstractXmlBuilder;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ServiceXml extends AbstractXmlBuilder {
    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);
    private final Settings settings;
    private SignValues signValues = new SignValues();
    private final boolean debug;
    private final ContainerConfig container;

    public ServiceXml(Settings jsonInputSettings, ContainerConfig container, boolean debug) {
        this.settings = jsonInputSettings;
        this.container = container;
        this.debug = debug;

        createServiceXml();
    }

    public void createServiceXml() {
        if ("create".equals(this.settings.getSignType())) {

            if ("SendRequestRequest".equals(this.settings.getDataType())) {
                createSendRequestRequest();
            } else if ("SendResponseRequest".equals(this.settings.getDataType())) {
                createSendResponseRequest();
            } else {
                setError("[CREATE SERVICEXML] unknown data type", log);
            }

        } else if ("sign".equals(this.settings.getSignType())) {

            if ("SendRequestRequest".equals(this.settings.getDataType())) {
                signSendRequestRequest(this.settings.getData());
            } else if ("SendResponseRequest".equals(this.settings.getDataType())) {
                signSendResponseRequest(this.settings.getData());
            } else {
                setError("[SIGN SERVICEXML] unknown data type", log);
            }

        } else {
            setError("[CREATESERVICEXML] unknown sign type", log);
        }
    }


    //ЗАПРОС [Ф] ПЕРЕПОДПИСАНИЕ
    private void signSendRequestRequest(String serviceXml) {
        SendRequestRequest serv = null;
        try {
            serv = (SendRequestRequest) Utils.convertXmlToJAXBObject(serviceXml, "smevsign.smev.v1_1.SendRequestRequest");
        } catch (Exception e) {
            setError("cast to SendRequestRequest: " + e.getMessage(), log);
            return;
        }
        if (serv != null) {
            try {
                this.signValues.setReferenceId(serv.getSenderProvidedRequestData().getId());
            } catch (Exception e) {
                setError("get sign id: " + e.getMessage(), log);
                return;
            }
            this.signValues.setElementNs(
                    "SenderProvidedRequestData",
                    "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1"
            );
            XMLDSigSignatureType dSign = new XMLDSigSignatureType();
            SignXml signXml = new SignXml(this.debug, this.container);
            Signature signature = signXml.sign(serv, this.signValues);
            if (signature != null) {
                dSign.setAny(Utils.getElementFromClass(signature));
                serv.setCallerInformationSystemSignature(dSign);
                setXml(Utils.convertJAXBObjectToXml(serv, "smevsign.smev.v1_1.SendRequestRequest", null));
            } else {
                setError("[SERVICEXML SENDREQUESTREQUEST] sign not created", log);
                setXml(null);
            }
        }
    }

    //ОТВЕТ [Р] ПЕРЕПОДПИСАНИЕ
    private void signSendResponseRequest(String serviceXml) {
        SendResponseRequest serv = null;
        try {
            serv = (SendResponseRequest) Utils.convertXmlToJAXBObject(serviceXml, "smevsign.smev.v1_1.SendResponseRequest");
        } catch (Exception e) {
            setError("cast to SendResponseRequest: " + e.getMessage(), log);
            return;
        }

        if (serv != null) {
            try {
                this.signValues.setReferenceId(serv.getSenderProvidedResponseData().getId());
            } catch (Exception e) {
                setError("get sign id: " + e.getMessage(), log);
                return;
            }
            this.signValues.setElementNs(
                    "SenderProvidedResponseData",
                    "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1"
            );

            XMLDSigSignatureType dSign = new XMLDSigSignatureType();
            SignXml signXml = new SignXml(this.debug, this.container);
            Signature signature = signXml.sign(serv, this.signValues);
            if (signature != null) {
                dSign.setAny(Utils.getElementFromClass(signature));
                serv.setCallerInformationSystemSignature(dSign);
                setXml(Utils.convertJAXBObjectToXml(serv, "smevsign.smev.v1_1.SendResponseRequest", null));
            } else {
                setError("[SERVICEXML SENDRESPONSEREQUEST] sign not created", log);
                setXml(null);
            }
        }
    }


    //ЗАПРОС [Ф] СОЗДАНИЕ
    public void createSendRequestRequest() {
        this.signValues.setReferenceId("SenderProvidedRequestDataId");
        this.signValues.setElementNs(
                "SenderProvidedRequestData",
                "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1"
        );

        boolean needCheckXmlSize = false;
        String serviceXml = this.settings.getData();
        boolean testMessage = true;
        String frguCode = "0000000000000000000";
        //String messageId = Utils.generateUuid1();
        String messageId = this.settings.getUuid();
        if (messageId == null) {
            setError("[BUILD SENDREQUESTREQUEST] messageId is null", log);
            setXml(null);
        }

        SendRequestRequest sendReq = new SendRequestRequest();
        SenderProvidedRequestData reqData = new SenderProvidedRequestData();
        reqData.setId(signValues.getReferenceId());
        reqData.setMessageID(messageId);
        reqData.setReferenceMessageID(messageId);

        //MessagePrimaryContent
        MessagePrimaryContent msgContent = new MessagePrimaryContent();
        Element any = Utils.getElementFromXmlString(serviceXml);
        if (any == null) {
            setError("[ERROR][createSendRequestRequest] stringToElement is null", log);
            return;
        }
        msgContent.setAny(any);
        reqData.setMessagePrimaryContent(msgContent);

        //files
        List<AttachmentInfo> files = settings.getFiles();
        if (files.size() > 0) {
            RefAttachmentHeaderList refAttachmentHeaderList = new RefAttachmentHeaderList();
            AttachmentHeaderList attachmentHeaderList = new AttachmentHeaderList();
            AttachmentContentList attachmentContentList = new AttachmentContentList();
            Tika tika = new Tika();
            SignFile sf = new SignFile(this.container, this.debug);
            try {
                for (AttachmentInfo file : files) {
                    File f = new File(String.format("%s%s", file.filePath, file.fileName));
                    if (f.exists()) {
                        sf.GeneratePkcs7(file, f);
                        if (file.pkcs7 != null) {
                            String mimeType = tika.detect(f);
                            if (settings.options.ftpUpload) {
                                RefAttachmentHeaderType refAttachmentHeaderType = new RefAttachmentHeaderType();
                                refAttachmentHeaderType.setUuid(file.ftpPath);
                                refAttachmentHeaderType.setMimeType(mimeType);
                                refAttachmentHeaderType.setHash(file.digest);
                                refAttachmentHeaderType.setSignaturePKCS7(file.pkcs7);
                                refAttachmentHeaderList.getRefAttachmentHeader().add(refAttachmentHeaderType);
                            } else {
                                AttachmentHeaderType attachmentHeaderType = new AttachmentHeaderType();
                                attachmentHeaderType.setContentId(file.contentId);
                                attachmentHeaderType.setMimeType(tika.detect(f));
                                attachmentHeaderType.setSignaturePKCS7(file.pkcs7);
                                attachmentHeaderList.getAttachmentHeader().add(attachmentHeaderType);

                                AttachmentContentType attachmentContentType = new AttachmentContentType();
                                attachmentContentType.setId(file.contentId);
                                DataSource ds = new FileDataSource(f.getAbsolutePath());
                                DataHandler handler = new DataHandler(ds);
                                attachmentContentType.setContent(handler);
                                attachmentContentList.getAttachmentContent().add(attachmentContentType);
                            }
                        }
                    } else {
                        setError(String.format("file not found: %s", file.fileName), log);
                        setXml(null);
                        return;
                    }
                }
            } catch (IOException ex) {
                setError(ex.getMessage(), log);
                setXml(null);
                return;
            }
            if (settings.options.ftpUpload) {
                if (refAttachmentHeaderList.getRefAttachmentHeader().size() > 0) {
                    reqData.setRefAttachmentHeaderList(refAttachmentHeaderList);
                }
            } else {
                if (attachmentHeaderList.getAttachmentHeader().size() > 0) {
                    needCheckXmlSize = true;
                    reqData.setAttachmentHeaderList(attachmentHeaderList);
                    sendReq.setAttachmentContentList(attachmentContentList);
                }
            }
        }

        //BusinessProcessMetadata
        if (frguCode != null) {
            SenderProvidedRequestData.BusinessProcessMetadata metaData = new SenderProvidedRequestData.BusinessProcessMetadata();
            String frgeNode = String.format("<fr:frgu xmlns:fr=\"urn://x-artefacts-smev-gov-ru/services/message-exchange/business-process-metadata/1.0\">%s</fr:frgu>", frguCode);
            Element anyMeta = Utils.getElementFromXmlString(frgeNode);
            metaData.getAny().add(0, anyMeta);
            reqData.setBusinessProcessMetadata(metaData);
        }

        //smev emulator key
        if (testMessage) {
            reqData.setTestMessage(new smevsign.smev.v1_1.Void());
        }

        sendReq.setSenderProvidedRequestData(reqData);

        XMLDSigSignatureType dSign = new XMLDSigSignatureType();
        sendReq.setCallerInformationSystemSignature(dSign);

        SignXml signXml = new SignXml(this.debug, this.container);
        Signature signature = signXml.sign(sendReq, this.signValues);
        if (signature != null) {
            dSign.setAny(Utils.getElementFromClass(signature));
            setXml(Utils.convertJAXBObjectToXml(sendReq, "smevsign.smev.v1_1.SendRequestRequest", null));
            if (needCheckXmlSize) {
                if (getXml().getBytes(StandardCharsets.UTF_8).length > 5 * 1024 * 1000) {
                    //smev xml size > 5mb
                    setError("[BUILD SENDREQUESTREQUEST] xml is too big", log);
                    setXml(null);
                }
            }
        } else {
            setError("[BUILD SENDREQUESTREQUEST] sign not created", log);
            setXml(null);
        }
    }

    //ОТВЕТ [Р] СОЗДАНИЕ
    private void createSendResponseRequest() {
        this.signValues.setReferenceId("SenderProvidedResponseDataId");
        this.signValues.setElementNs(
                "SenderProvidedResponseData",
                "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1"
        );

        SendResponseRequest sendRes = new SendResponseRequest();
    }

}
