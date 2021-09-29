//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.06 at 06:29:18 AM MSK 
//


package smevsign.smev.v1_2;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InteractionTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InteractionTypeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PGU2OIV"/&gt;
 *     &lt;enumeration value="OIV2OIV"/&gt;
 *     &lt;enumeration value="OIV2sameOIV"/&gt;
 *     &lt;enumeration value="Charger2PaymentGate"/&gt;
 *     &lt;enumeration value="PaymentGate2Treasury"/&gt;
 *     &lt;enumeration value="OIV2Treasury"/&gt;
 *     &lt;enumeration value="other"/&gt;
 *     &lt;enumeration value="NotDetected"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "InteractionTypeType", namespace = "urn://x-artefacts-smev-gov-ru/services/message-exchange/types/basic/1.2")
@XmlEnum
public enum InteractionTypeType {


    /**
     * Взаимодействие портала государственных и/или муниципальных услуг с органом власти.
     * 
     */
    @XmlEnumValue("PGU2OIV")
    PGU_2_OIV("PGU2OIV"),

    /**
     * Взаимодействие между органами власти.
     * 
     */
    @XmlEnumValue("OIV2OIV")
    OIV_2_OIV("OIV2OIV"),

    /**
     * Взаимодействие между различными информационными системами одного органа исполнительной власти через СМЭВ.
     * 
     */
    @XmlEnumValue("OIV2sameOIV")
    OIV_2_SAME_OIV("OIV2sameOIV"),

    /**
     * Взаимодействие информационно-платежного шлюза с поставщиками начислений для оплаты услуг в электронном виде.
     * 
     */
    @XmlEnumValue("Charger2PaymentGate")
    CHARGER_2_PAYMENT_GATE("Charger2PaymentGate"),

    /**
     * Взаимодействие информационно-платежного шлюза с системой УНИФО ФК для получения начислений и фактов оплаты для пользователей ПГУ.
     * 
     */
    @XmlEnumValue("PaymentGate2Treasury")
    PAYMENT_GATE_2_TREASURY("PaymentGate2Treasury"),

    /**
     * Взаимодействие ОИВ с системой УНИФО ФК для передачи начислений и получения фактов оплаты.
     * 
     */
    @XmlEnumValue("OIV2Treasury")
    OIV_2_TREASURY("OIV2Treasury"),

    /**
     * Другие типы взаимодействия.
     * 
     */
    @XmlEnumValue("other")
    OTHER("other"),

    /**
     * Не удалось определить тип взаимодействия.
     * 
     */
    @XmlEnumValue("NotDetected")
    NOT_DETECTED("NotDetected");
    private final String value;

    InteractionTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InteractionTypeType fromValue(String v) {
        for (InteractionTypeType c: InteractionTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
