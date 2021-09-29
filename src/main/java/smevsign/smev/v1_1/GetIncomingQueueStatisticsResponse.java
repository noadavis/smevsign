//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:21 AM MSK 
//


package smevsign.smev.v1_1;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="QueueStatistics" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="queueName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1}string-200" /&gt;
 *                 &lt;attribute name="pendingMessageNumber" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "queueStatistics"
})
@XmlRootElement(name = "GetIncomingQueueStatisticsResponse")
public class GetIncomingQueueStatisticsResponse {

    @XmlElement(name = "QueueStatistics")
    protected List<GetIncomingQueueStatisticsResponse.QueueStatistics> queueStatistics;

    /**
     * Gets the value of the queueStatistics property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the queueStatistics property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQueueStatistics().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetIncomingQueueStatisticsResponse.QueueStatistics }
     * 
     * 
     */
    public List<GetIncomingQueueStatisticsResponse.QueueStatistics> getQueueStatistics() {
        if (queueStatistics == null) {
            queueStatistics = new ArrayList<GetIncomingQueueStatisticsResponse.QueueStatistics>();
        }
        return this.queueStatistics;
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
     *       &lt;attribute name="queueName" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1}string-200" /&gt;
     *       &lt;attribute name="pendingMessageNumber" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class QueueStatistics {

        @XmlAttribute(name = "queueName")
        protected String queueName;
        @XmlAttribute(name = "pendingMessageNumber")
        protected Long pendingMessageNumber;

        /**
         * Gets the value of the queueName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQueueName() {
            return queueName;
        }

        /**
         * Sets the value of the queueName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQueueName(String value) {
            this.queueName = value;
        }

        /**
         * Gets the value of the pendingMessageNumber property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getPendingMessageNumber() {
            return pendingMessageNumber;
        }

        /**
         * Sets the value of the pendingMessageNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setPendingMessageNumber(Long value) {
            this.pendingMessageNumber = value;
        }

    }

}
