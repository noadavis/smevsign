package smevsign.support;

import com.google.gson.annotations.SerializedName;

public class PersonalSign {
    @SerializedName(value = "digest_value")
    public String digestValue = "";
    @SerializedName(value = "certificate")
    public String certificate = "";
    public String algorithm = "";
    @SerializedName(value = "signature_value")
    public String signatureValue = "";
}