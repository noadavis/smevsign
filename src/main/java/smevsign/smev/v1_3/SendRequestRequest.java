//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:15 AM MSK 
//


package smevsign.smev.v1_3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.3}SenderProvidedRequestData"/&gt;
 *         &lt;element ref="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.3}AttachmentContentList" minOccurs="0"/&gt;
 *         &lt;element name="CallerInformationSystemSignature" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.3}XMLDSigSignatureType" minOccurs="0"/&gt;
 *         &lt;element ref="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/routing/1.3}Routing" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "senderProvidedRequestData",
    "attachmentContentList",
    "callerInformationSystemSignature",
    "routing"
})
@XmlRootElement(name = "SendRequestRequest")
public class SendRequestRequest {

    @XmlElement(name = "SenderProvidedRequestData", required = true)
    protected SenderProvidedRequestData senderProvidedRequestData;
    @XmlElement(name = "AttachmentContentList", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.3")
    protected AttachmentContentList attachmentContentList;
    @XmlElement(name = "CallerInformationSystemSignature")
    protected XMLDSigSignatureType callerInformationSystemSignature;
    @XmlElement(name = "Routing", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/routing/1.3")
    protected Routing routing;

    /**
     * Содержательная часть запроса + служебные данные, заполняемые отправителем.
     * 
     * @return
     *     possible object is
     *     {@link SenderProvidedRequestData }
     *     
     */
    public SenderProvidedRequestData getSenderProvidedRequestData() {
        return senderProvidedRequestData;
    }

    /**
     * Sets the value of the senderProvidedRequestData property.
     * 
     * @param value
     *     allowed object is
     *     {@link SenderProvidedRequestData }
     *     
     */
    public void setSenderProvidedRequestData(SenderProvidedRequestData value) {
        this.senderProvidedRequestData = value;
    }

    /**
     * Вложенные файлы - содержимое. Содержимое вынесено из-под ЭП-ОВ, чтобы не нарушать работу MTOM.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentContentList }
     *     
     */
    public AttachmentContentList getAttachmentContentList() {
        return attachmentContentList;
    }

    /**
     * Sets the value of the attachmentContentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentContentList }
     *     
     */
    public void setAttachmentContentList(AttachmentContentList value) {
        this.attachmentContentList = value;
    }

    /**
     * Gets the value of the callerInformationSystemSignature property.
     * 
     * @return
     *     possible object is
     *     {@link XMLDSigSignatureType }
     *     
     */
    public XMLDSigSignatureType getCallerInformationSystemSignature() {
        return callerInformationSystemSignature;
    }

    /**
     * Sets the value of the callerInformationSystemSignature property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLDSigSignatureType }
     *     
     */
    public void setCallerInformationSystemSignature(XMLDSigSignatureType value) {
        this.callerInformationSystemSignature = value;
    }

    /**
     * Маршрутизация для сообщения с директивными ВВС (только для МР 3.5)
     * 
     * @return
     *     possible object is
     *     {@link Routing }
     *     
     */
    public Routing getRouting() {
        return routing;
    }

    /**
     * Sets the value of the routing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Routing }
     *     
     */
    public void setRouting(Routing value) {
        this.routing = value;
    }

}