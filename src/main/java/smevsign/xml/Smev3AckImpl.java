package smevsign.xml;

import smevsign.support.ContainerConfig;
import smevsign.support.Settings;

public class Smev3AckImpl {
    private Smev3AckImpl() {}
    public static Smev3Ack getAckXml(boolean accepted, Settings jsonInputSettings, ContainerConfig container, boolean debug) {
        switch (jsonInputSettings.getXmlScheme()) {
            case "1.1":
                return new smevsign.xml.v1_1.AckXml(accepted, jsonInputSettings, container, debug);
            case "1.2":
                return new smevsign.xml.v1_2.AckXml(accepted, jsonInputSettings, container, debug);
            case "1.3":
                return new smevsign.xml.v1_3.AckXml(accepted, jsonInputSettings, container, debug);
            default:
                return null;
        }
    }
}
