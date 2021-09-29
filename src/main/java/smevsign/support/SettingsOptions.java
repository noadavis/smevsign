package smevsign.support;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class SettingsOptions {
    public String xmlScheme = null;
    public String dataType = null;
    public String signType = null;
    public String uuid = null;
    public String path = null;
    public List<String> files = new ArrayList<String>();
    public boolean signature_detached = true;
    public String zipName = null;
}