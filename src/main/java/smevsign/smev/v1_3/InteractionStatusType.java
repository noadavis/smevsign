//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:15 AM MSK 
//


package smevsign.smev.v1_3;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InteractionStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InteractionStatusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="doesNotExist"/&gt;
 *     &lt;enumeration value="requestIsQueued"/&gt;
 *     &lt;enumeration value="requestIsAcceptedBySmev"/&gt;
 *     &lt;enumeration value="requestIsRejectedBySmev"/&gt;
 *     &lt;enumeration value="requestIsProcessed"/&gt;
 *     &lt;enumeration value="underProcessing"/&gt;
 *     &lt;enumeration value="responseIsQueued"/&gt;
 *     &lt;enumeration value="responseIsAcceptedBySmev"/&gt;
 *     &lt;enumeration value="responseIsRejectedBySmev"/&gt;
 *     &lt;enumeration value="responseIsProcessed"/&gt;
 *     &lt;enumeration value="cancelled"/&gt;
 *     &lt;enumeration value="messageIsArchived"/&gt;
 *     &lt;enumeration value="messageIsDelivered"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "InteractionStatusType", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.3")
@XmlEnum
public enum InteractionStatusType {


    /**
     * Запрос с таким Id не найден в БД СМЭВ.
     * 
     */
    @XmlEnumValue("doesNotExist")
    DOES_NOT_EXIST("doesNotExist"),

    /**
     * Запрос находится в очереди на асинхронную валидацию.
     * 
     */
    @XmlEnumValue("requestIsQueued")
    REQUEST_IS_QUEUED("requestIsQueued"),

    /**
     * Запрос прошел асинхронную валидацию.
     * 
     */
    @XmlEnumValue("requestIsAcceptedBySmev")
    REQUEST_IS_ACCEPTED_BY_SMEV("requestIsAcceptedBySmev"),

    /**
     * Запрос не прошёл асинхронную валидацию.
     * 
     */
    @XmlEnumValue("requestIsRejectedBySmev")
    REQUEST_IS_REJECTED_BY_SMEV("requestIsRejectedBySmev"),

    /**
     * Запрос обработан СМЭВ.
     * 
     */
    @XmlEnumValue("requestIsProcessed")
    REQUEST_IS_PROCESSED("requestIsProcessed"),

    /**
     * Обрабатывается поставщиком сервиса.
     * 
     */
    @XmlEnumValue("underProcessing")
    UNDER_PROCESSING("underProcessing"),

    /**
     * Ответ на запрос находится в очереди на асинхронную валидацию.
     * 
     */
    @XmlEnumValue("responseIsQueued")
    RESPONSE_IS_QUEUED("responseIsQueued"),

    /**
     * Ответ на запрос прошел асинхронную валидацию.
     * 
     */
    @XmlEnumValue("responseIsAcceptedBySmev")
    RESPONSE_IS_ACCEPTED_BY_SMEV("responseIsAcceptedBySmev"),

    /**
     * Ответ на запрос не прошёл асинхронную валидацию.
     * 
     */
    @XmlEnumValue("responseIsRejectedBySmev")
    RESPONSE_IS_REJECTED_BY_SMEV("responseIsRejectedBySmev"),

    /**
     * Ответ на запрос обработан СМЭВ.
     * 
     */
    @XmlEnumValue("responseIsProcessed")
    RESPONSE_IS_PROCESSED("responseIsProcessed"),

    /**
     * Запрос отменён сервисом.
     * 
     */
    @XmlEnumValue("cancelled")
    CANCELLED("cancelled"),

    /**
     * Сообщение переведено в архив.
     * 
     */
    @XmlEnumValue("messageIsArchived")
    MESSAGE_IS_ARCHIVED("messageIsArchived"),

    /**
     * Сообщение получено получателем.
     * 
     */
    @XmlEnumValue("messageIsDelivered")
    MESSAGE_IS_DELIVERED("messageIsDelivered");
    private final String value;

    InteractionStatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InteractionStatusType fromValue(String v) {
        for (InteractionStatusType c: InteractionStatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
