package smevsign.xml;

import smevsign.support.ContainerConfig;
import smevsign.support.Settings;

import java.util.List;

public class Smev3XmlImpl {
    private Smev3XmlImpl() {}
    public static Smev3Xml getServiceXml(Settings jsonInputSettings, ContainerConfig container, List<String> algorithmTypes, boolean debug) {
        switch (jsonInputSettings.getXmlScheme()) {
            case "1.1":
                return new smevsign.xml.v1_1.ServiceXml(jsonInputSettings, container, algorithmTypes, debug);
            default:
                return null;
        }
    }
}
