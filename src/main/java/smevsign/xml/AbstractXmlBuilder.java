package smevsign.xml;

import org.apache.commons.logging.Log;
import smevsign.support.DigestValue;
import smevsign.support.ServiceAnswer;

import java.util.List;

public class AbstractXmlBuilder {
    private String errorDescription = "";
    private boolean error = false;
    private String xml = null;
    private String value = null;
    private List<DigestValue> digests = null;

    public String getXmlFromBuilder(ServiceAnswer serviceAnswer) {
        if (isError()) {
            //setError(getErrorDescription(), serviceAnswer);
        } else {
            return getXml();
        }
        return null;
    }

    public void setError(String errDesc, Log log) {
        this.error = true;
        this.errorDescription = errDesc;
        log.info(errDesc);
    }
    public boolean isError() {
        return error;
    }
    public String getErrorDescription() {
        return errorDescription;
    }
    public String getXml() {
        return this.xml;
    }
    public void setXml(String xml) {
        this.xml = xml;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public List<DigestValue> getDigests() {
        return this.digests;
    }
    public void setDigests(List<DigestValue> digests) {
        this.digests = digests;
    }
}
