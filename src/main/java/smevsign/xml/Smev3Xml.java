package smevsign.xml;

import smevsign.support.DigestValue;

import java.util.List;

public interface Smev3Xml {
    public void createServiceXml();
    public boolean isError();
    public String getErrorDescription();
    public String getXml();
    public List<DigestValue> getDigests();
}
