//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:15 AM MSK 
//


package smevsign.smev.v1_3;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="AttachmentContent" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.3}AttachmentContentType" maxOccurs="unbounded"/&gt;
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
    "attachmentContent"
})
@XmlRootElement(name = "AttachmentContentList", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.3")
public class AttachmentContentList {

    @XmlElement(name = "AttachmentContent", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.3", required = true)
    protected List<AttachmentContentType> attachmentContent;

    /**
     * Gets the value of the attachmentContent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachmentContent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachmentContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachmentContentType }
     * 
     * 
     */
    public List<AttachmentContentType> getAttachmentContent() {
        if (attachmentContent == null) {
            attachmentContent = new ArrayList<AttachmentContentType>();
        }
        return this.attachmentContent;
    }

}
