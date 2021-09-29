package smevsign.support;

import org.apache.commons.codec.binary.Base64;

public class DigestValue {
    String algorithm;
    String value;
    String signed_info;

    public DigestValue(String algorithm, byte[] value, byte[] signed_info) {
        this.algorithm = algorithm;
        this.value = Base64.encodeBase64String(value);
        this.signed_info = Base64.encodeBase64String(signed_info);
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DigestValue() {
    }
}