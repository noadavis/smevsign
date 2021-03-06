//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:21 AM MSK 
//


package smevsign.smev.v1_1;

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
 * 
 *                     Селектор, с помощью которого при приёме запроса или ответа
 *                     можно задать фильтр по типу запроса (ответа).
 *                     Поскольку тип запроса или ответа однозначно определяется полным именем
 *                     корневого XML-элемента его бизнес-данных,
 *                     селектор представляет из себя структуру для задания этого имени.
 *                     Если селектор пуст, это значит, что нужно принять запрос (ответ)
 *                     без фильтрации по типам.
 *                 
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
 *         &lt;sequence minOccurs="0"&gt;
 *           &lt;element name="NamespaceURI" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *           &lt;element name="RootElementLocalName" type="{http://www.w3.org/2001/XMLSchema}NCName"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="NodeID" type="{urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1}string-50" minOccurs="0"/&gt;
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
    "namespaceURI",
    "rootElementLocalName",
    "timestamp",
    "nodeID"
})
@XmlRootElement(name = "MessageTypeSelector", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1")
public class MessageTypeSelector {

    @XmlElement(name = "NamespaceURI", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1")
    @XmlSchemaType(name = "anyURI")
    protected String namespaceURI;
    @XmlElement(name = "RootElementLocalName", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String rootElementLocalName;
    @XmlElement(name = "Timestamp", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "NodeID", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.1")
    protected String nodeID;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the namespaceURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

    /**
     * Sets the value of the namespaceURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamespaceURI(String value) {
        this.namespaceURI = value;
    }

    /**
     * Gets the value of the rootElementLocalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootElementLocalName() {
        return rootElementLocalName;
    }

    /**
     * Sets the value of the rootElementLocalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootElementLocalName(String value) {
        this.rootElementLocalName = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the nodeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeID() {
        return nodeID;
    }

    /**
     * Sets the value of the nodeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeID(String value) {
        this.nodeID = value;
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

}
