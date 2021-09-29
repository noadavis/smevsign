//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:18 AM MSK 
//


package smevsign.smev.v1_2;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Маршрутная информация, заполняемая СМЭВ.
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MessageId" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}UUID"/&gt;
 *         &lt;element name="MessageType" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.2}MessageTypeType"/&gt;
 *         &lt;element name="Sender"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Mnemonic" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-50"/&gt;
 *                   &lt;element name="HumanReadableName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-500"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SendingTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DestinationName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-500"/&gt;
 *         &lt;element name="Recipient" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Mnemonic" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-100"/&gt;
 *                   &lt;element name="HumanReadableName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-500"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SupplementaryData"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="DetectedContentTypeName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-500" minOccurs="0"/&gt;
 *                   &lt;element name="InteractionType" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}InteractionTypeType"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DeliveryTimestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}InteractionStatusType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "messageId",
    "messageType",
    "sender",
    "sendingTimestamp",
    "destinationName",
    "recipient",
    "supplementaryData",
    "deliveryTimestamp",
    "status"
})
@XmlRootElement(name = "MessageMetadata")
public class MessageMetadata {

    @XmlElement(name = "MessageId", required = true)
    protected String messageId;
    @XmlElement(name = "MessageType", required = true)
    @XmlSchemaType(name = "string")
    protected MessageTypeType messageType;
    @XmlElement(name = "Sender", required = true)
    protected MessageMetadata.Sender sender;
    @XmlElement(name = "SendingTimestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sendingTimestamp;
    @XmlElement(name = "DestinationName", required = true)
    protected String destinationName;
    @XmlElement(name = "Recipient")
    protected MessageMetadata.Recipient recipient;
    @XmlElement(name = "SupplementaryData", required = true)
    protected MessageMetadata.SupplementaryData supplementaryData;
    @XmlElement(name = "DeliveryTimestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar deliveryTimestamp;
    @XmlElement(name = "Status")
    @XmlSchemaType(name = "string")
    protected InteractionStatusType status;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the messageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Sets the value of the messageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageId(String value) {
        this.messageId = value;
    }

    /**
     * Gets the value of the messageType property.
     * 
     * @return
     *     possible object is
     *     {@link MessageTypeType }
     *     
     */
    public MessageTypeType getMessageType() {
        return messageType;
    }

    /**
     * Sets the value of the messageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageTypeType }
     *     
     */
    public void setMessageType(MessageTypeType value) {
        this.messageType = value;
    }

    /**
     * Gets the value of the sender property.
     * 
     * @return
     *     possible object is
     *     {@link MessageMetadata.Sender }
     *     
     */
    public MessageMetadata.Sender getSender() {
        return sender;
    }

    /**
     * Sets the value of the sender property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageMetadata.Sender }
     *     
     */
    public void setSender(MessageMetadata.Sender value) {
        this.sender = value;
    }

    /**
     * Gets the value of the sendingTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSendingTimestamp() {
        return sendingTimestamp;
    }

    /**
     * Sets the value of the sendingTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSendingTimestamp(XMLGregorianCalendar value) {
        this.sendingTimestamp = value;
    }

    /**
     * Gets the value of the destinationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationName() {
        return destinationName;
    }

    /**
     * Sets the value of the destinationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationName(String value) {
        this.destinationName = value;
    }

    /**
     * Gets the value of the recipient property.
     * 
     * @return
     *     possible object is
     *     {@link MessageMetadata.Recipient }
     *     
     */
    public MessageMetadata.Recipient getRecipient() {
        return recipient;
    }

    /**
     * Sets the value of the recipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageMetadata.Recipient }
     *     
     */
    public void setRecipient(MessageMetadata.Recipient value) {
        this.recipient = value;
    }

    /**
     * Gets the value of the supplementaryData property.
     * 
     * @return
     *     possible object is
     *     {@link MessageMetadata.SupplementaryData }
     *     
     */
    public MessageMetadata.SupplementaryData getSupplementaryData() {
        return supplementaryData;
    }

    /**
     * Sets the value of the supplementaryData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageMetadata.SupplementaryData }
     *     
     */
    public void setSupplementaryData(MessageMetadata.SupplementaryData value) {
        this.supplementaryData = value;
    }

    /**
     * Gets the value of the deliveryTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeliveryTimestamp() {
        return deliveryTimestamp;
    }

    /**
     * Sets the value of the deliveryTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeliveryTimestamp(XMLGregorianCalendar value) {
        this.deliveryTimestamp = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link InteractionStatusType }
     *     
     */
    public InteractionStatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link InteractionStatusType }
     *     
     */
    public void setStatus(InteractionStatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }


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
     *         &lt;element name="Mnemonic" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-100"/&gt;
     *         &lt;element name="HumanReadableName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-500"/&gt;
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
        "mnemonic",
        "humanReadableName"
    })
    public static class Recipient {

        @XmlElement(name = "Mnemonic", required = true)
        protected String mnemonic;
        @XmlElement(name = "HumanReadableName", required = true)
        protected String humanReadableName;

        /**
         * Gets the value of the mnemonic property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMnemonic() {
            return mnemonic;
        }

        /**
         * Sets the value of the mnemonic property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMnemonic(String value) {
            this.mnemonic = value;
        }

        /**
         * Gets the value of the humanReadableName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHumanReadableName() {
            return humanReadableName;
        }

        /**
         * Sets the value of the humanReadableName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHumanReadableName(String value) {
            this.humanReadableName = value;
        }

    }


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
     *         &lt;element name="Mnemonic" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-50"/&gt;
     *         &lt;element name="HumanReadableName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-500"/&gt;
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
        "mnemonic",
        "humanReadableName"
    })
    public static class Sender {

        @XmlElement(name = "Mnemonic", required = true)
        protected String mnemonic;
        @XmlElement(name = "HumanReadableName", required = true)
        protected String humanReadableName;

        /**
         * Gets the value of the mnemonic property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMnemonic() {
            return mnemonic;
        }

        /**
         * Sets the value of the mnemonic property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMnemonic(String value) {
            this.mnemonic = value;
        }

        /**
         * Gets the value of the humanReadableName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHumanReadableName() {
            return humanReadableName;
        }

        /**
         * Sets the value of the humanReadableName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHumanReadableName(String value) {
            this.humanReadableName = value;
        }

    }


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
     *         &lt;element name="DetectedContentTypeName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}string-500" minOccurs="0"/&gt;
     *         &lt;element name="InteractionType" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}InteractionTypeType"/&gt;
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
        "detectedContentTypeName",
        "interactionType"
    })
    public static class SupplementaryData {

        @XmlElement(name = "DetectedContentTypeName")
        protected String detectedContentTypeName;
        @XmlElement(name = "InteractionType", required = true)
        @XmlSchemaType(name = "string")
        protected InteractionTypeType interactionType;

        /**
         * Gets the value of the detectedContentTypeName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDetectedContentTypeName() {
            return detectedContentTypeName;
        }

        /**
         * Sets the value of the detectedContentTypeName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDetectedContentTypeName(String value) {
            this.detectedContentTypeName = value;
        }

        /**
         * Gets the value of the interactionType property.
         * 
         * @return
         *     possible object is
         *     {@link InteractionTypeType }
         *     
         */
        public InteractionTypeType getInteractionType() {
            return interactionType;
        }

        /**
         * Sets the value of the interactionType property.
         * 
         * @param value
         *     allowed object is
         *     {@link InteractionTypeType }
         *     
         */
        public void setInteractionType(InteractionTypeType value) {
            this.interactionType = value;
        }

    }

}
