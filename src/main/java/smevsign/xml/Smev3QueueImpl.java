package smevsign.xml;

import smevsign.support.ContainerConfig;
import smevsign.support.Settings;

public class Smev3QueueImpl {
    private Smev3QueueImpl() {}
    public static Smev3Queue getQueueXml(Settings jsonInputSettings, ContainerConfig container, boolean debug) {
        switch (jsonInputSettings.getXmlScheme()) {
            case "1.1":
                return new smevsign.xml.v1_1.QueueXml(jsonInputSettings, container, debug);
            case "1.2":
                return new smevsign.xml.v1_2.QueueXml(jsonInputSettings, container, debug);
            case "1.3":
                return new smevsign.xml.v1_3.QueueXml(jsonInputSettings, container, debug);
            default:
                return null;
        }
    }
}
