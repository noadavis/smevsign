package smevsign.xml;

public interface Smev3Xml {
    public void createServiceXml();
    public boolean isError();
    public String getErrorDescription();
    public String getXml();
}
