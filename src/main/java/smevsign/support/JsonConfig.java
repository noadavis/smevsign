package smevsign.support;

import java.util.List;

public class JsonConfig {
    public List<ContainerConfig> getContainers() {
        return containers;
    }
    public List<ContainerConfig> containers;
    public String alias;
    public boolean debug = false;
    public List<String> algorithms;
    public JsonConfig() {
    }
}
