//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:18 AM MSK 
//


package smevsign.smev.v1_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


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
 *     &lt;enumeration value="underProcessing"/&gt;
 *     &lt;enumeration value="responseIsAcceptedBySmev"/&gt;
 *     &lt;enumeration value="cancelled"/&gt;
 *     &lt;enumeration value="responseIsDelivered"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "InteractionStatusType", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2")
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
     * Запрос доставляется поставщику.
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
     * Обрабатывается поставщиком сервиса.
     * 
     */
    @XmlEnumValue("underProcessing")
    UNDER_PROCESSING("underProcessing"),

    /**
     * Запрос выполнен или отвергнут поставщиком сервиса; ответ находится в очереди СМЭВ.
     * 
     */
    @XmlEnumValue("responseIsAcceptedBySmev")
    RESPONSE_IS_ACCEPTED_BY_SMEV("responseIsAcceptedBySmev"),

    /**
     * Запрос отменён потребителем сервиса.
     * 
     */
    @XmlEnumValue("cancelled")
    CANCELLED("cancelled"),

    /**
     * Ответ получен потребителем сервиса.
     * 
     */
    @XmlEnumValue("responseIsDelivered")
    RESPONSE_IS_DELIVERED("responseIsDelivered");
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
