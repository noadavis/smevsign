//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:21 AM MSK 
//


package smevsign.smev.v1_1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1}MessageTypeSelector"/&gt;
 *         &lt;element name="CallerInformationSystemSignature" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1}XMLDSigSignatureType" minOccurs="0"/&gt;
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
    "messageTypeSelector",
    "callerInformationSystemSignature"
})
@XmlRootElement(name = "GetResponseRequest")
public class GetResponseRequest {

    @XmlElement(name = "MessageTypeSelector", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1", required = true)
    protected MessageTypeSelector messageTypeSelector;
    @XmlElement(name = "CallerInformationSystemSignature")
    protected XMLDSigSignatureType callerInformationSystemSignature;

    /**
     * 
     *                             См. описание {urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1}MessageTypeSelector
     *                         
     * 
     * @return
     *     possible object is
     *     {@link MessageTypeSelector }
     *     
     */
    public MessageTypeSelector getMessageTypeSelector() {
        return messageTypeSelector;
    }

    /**
     * Sets the value of the messageTypeSelector property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageTypeSelector }
     *     
     */
    public void setMessageTypeSelector(MessageTypeSelector value) {
        this.messageTypeSelector = value;
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

}
