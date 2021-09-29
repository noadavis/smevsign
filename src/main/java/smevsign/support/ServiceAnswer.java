package smevsign.support;

import java.util.List;

public class ServiceAnswer {
    public String errorDescription = "";
    public String serviceAnswer = "";
    public List<DigestValue> digest = null;
    public boolean error = false;
    public boolean isDigestExist() {
        return digest != null && digest.size() > 0;
    }
}
