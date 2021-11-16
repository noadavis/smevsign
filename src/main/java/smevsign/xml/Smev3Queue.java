package smevsign.xml;

public interface Smev3Queue {
    public void createQueueXml();
    public boolean isError();
    public String getErrorDescription();
    public String getXml();
}
