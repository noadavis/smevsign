package smevsign.support;

import com.google.gson.annotations.SerializedName;

public class SettingsOptions {
    @SerializedName(value = "xml_scheme")
    public String xmlScheme = "1.1";
    @SerializedName(value = "data_type")
    public String dataType = null;
    @SerializedName(value = "sign_type")
    public String signType = null;
    public String uuid = null;
    @SerializedName(value = "signature_detached")
    public boolean signatureDetached = true;
    @SerializedName(value = "zip_name")
    public String zipName = null;
    @SerializedName(value = "ftp_upload")
    public boolean ftpUpload = true;
    @SerializedName(value = "root_element")
    public String rootElement = null;
    public String namespace = null;
    @SerializedName(value = "url_safe")
    public boolean urlSafe = false;
}