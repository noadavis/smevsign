package smevsign.support;

import com.google.gson.annotations.SerializedName;
import smevsign.smev.Utils;

public class AttachmentInfo {
    @SerializedName(value = "ftp_path")
    public String ftpPath = null;
    @SerializedName(value = "file_path")
    public String filePath = null;
    @SerializedName(value = "file_name")
    public String fileName = null;
    public String digest = null;
    public byte[] pkcs7 = null;
    @SerializedName(value = "content_id")
    public String contentId = null;

    public AttachmentInfo(String ftpPath, String filePath, String fileName) {
        this.ftpPath = ftpPath;
        this.filePath = filePath;
        this.fileName = fileName;
        this.contentId = Utils.generateUuid4();
    }
    public void generateContentId() {
        this.contentId = Utils.generateUuid4();
    }
}