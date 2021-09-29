package smevsign.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    @SerializedName(value = "request_type")
    public String requestType = "";
    public String data = "";
    @SerializedName(value = "sign_alias")
    public String signAlias = null;
    public SettingsOptions options = new SettingsOptions();
    public List<AttachmentInfo> files = null;
    @SerializedName(value = "personal_sign")
    public PersonalSign personalSign = new PersonalSign();

    public String getRequestType() {
        return requestType;
    }
    public String getDataType() {
        return options.dataType;
    }
    public String getData() {
        return data;
    }
    public void setDataType(String dataType) {
        options.dataType = dataType;
    }
    public String getSignType() {
        return options.signType;
    }
    public void setSignType(String signType) {
        options.signType = signType;
    }
    public String getXmlScheme() {
        return options.xmlScheme;
    }
    public String getUuid() {
        return options.uuid;
    }
    public String getSignAlias() {
        return signAlias;
    }
    public void generateContentId() {
        List<AttachmentInfo> files = getFiles();
        if (files.size() > 0) {
            for (AttachmentInfo file : files) {
                file.generateContentId();
            }
        }
    }
    public boolean checkContentId() {
        List<AttachmentInfo> files = getFiles();
        if (files.size() > 0) {
            for (AttachmentInfo file : files) {
                if (file.contentId == null) {
                    return false;
                    //file.generateContentId();
                }
            }
        }
        return true;
    }
    public List<AttachmentInfo> getFiles() {
        if (files == null) {
            files = new ArrayList<AttachmentInfo>();
        }
        return files;
    }

    public String toString() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
