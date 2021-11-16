package smevsign.xml;

public interface Smev3Ack {
    public void createAckXml();
    public boolean isError();
    public String getErrorDescription();
    public String getXml();
}
