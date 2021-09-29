package smevsign.support;

public class Settings {
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
    //"sign_xml", "sign_file", "sign_string", "queue_xml", "ack_xml"
    public String requestType = "";
    public String data = "";
    public String signAlias = null;
    public SettingsOptions options = new SettingsOptions();
}
