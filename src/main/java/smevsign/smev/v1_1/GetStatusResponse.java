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
 *         &lt;element ref="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/1.1}SmevAsyncProcessingMessage" minOccurs="0"/&gt;
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
    "smevAsyncProcessingMessage"
})
@XmlRootElement(name = "GetStatusResponse")
public class GetStatusResponse {

    @XmlElement(name = "SmevAsyncProcessingMessage")
    protected SmevAsyncProcessingMessage smevAsyncProcessingMessage;

    /**
     * Gets the value of the smevAsyncProcessingMessage property.
     * 
     * @return
     *     possible object is
     *     {@link SmevAsyncProcessingMessage }
     *     
     */
    public SmevAsyncProcessingMessage getSmevAsyncProcessingMessage() {
        return smevAsyncProcessingMessage;
    }

    /**
     * Sets the value of the smevAsyncProcessingMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link SmevAsyncProcessingMessage }
     *     
     */
    public void setSmevAsyncProcessingMessage(SmevAsyncProcessingMessage value) {
        this.smevAsyncProcessingMessage = value;
    }

}
