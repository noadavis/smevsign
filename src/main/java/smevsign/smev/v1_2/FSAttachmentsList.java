//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:18 AM MSK 
//


package smevsign.smev.v1_2;

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
 *         &lt;element name="FSAttachment" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2}FSAuthInfo" maxOccurs="unbounded"/&gt;
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
    "fsAttachment"
})
@XmlRootElement(name = "FSAttachmentsList", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2")
public class FSAttachmentsList {

    @XmlElement(name = "FSAttachment", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2", required = true)
    protected List<FSAuthInfo> fsAttachment;

    /**
     * Gets the value of the fsAttachment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fsAttachment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFSAttachment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FSAuthInfo }
     * 
     * 
     */
    public List<FSAuthInfo> getFSAttachment() {
        if (fsAttachment == null) {
            fsAttachment = new ArrayList<FSAuthInfo>();
        }
        return this.fsAttachment;
    }

}
