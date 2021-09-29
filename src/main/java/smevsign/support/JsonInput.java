package smevsign.support;

import com.google.gson.*;
import org.apache.commons.codec.binary.Base64;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonInput {

    private boolean error = false;
    private String errorDescription = "";
    //private String servicePort;
    //private String serviceUrl;
    //private final List<String> jsonRequestType = new ArrayList<>(List.of("sign_xml", "sign_file", "sign_string", "queue_xml", "ack_xml"));

    private void setError(String message) {
        this.error = true;
        this.errorDescription = message;
    }

    public boolean isError() {
        return this.error;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    private String getJsonInputString(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            this.error = true;
            this.errorDescription = "getJsonInputString IOException: " + e.getMessage();
        }
        System.out.println("getJsonInputString json: " + sb.toString());
        return sb.toString();
    }

    public void check(Settings settings) {
        if (error) {
            return;
        }
        switch (settings.getRequestType()) {
            case "sign_xml":
                if (settings.getData() == null) {
                    setError("data not set");
                }
                if (settings.getSignType() == null) {
                    setError("sign_type not set [create, sign]");
                }
                if (!settings.options.ftpUpload) {
                    if (!settings.checkContentId()) {
                        setError("content id for file not set");
                    }
                }
                break;
            case "queue_xml":
                //todo create queue xml checks
                break;
            case "ack_xml":
                if ("AckRequest".equals(settings.getDataType())) {
                    if (settings.getUuid() == null) {
                        setError("uuid not set");
                    }
                }
                break;
            case "sign_file":
                //todo create sign file checks
                break;
            default:
                setError("request type unknown");
                break;
        }
    }

    public Settings parse(HttpServletRequest req) {
        String jsonString = getJsonInputString(req);
        Settings settings = null;
        if (!this.error) {
            try {
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                settings = gson.fromJson(jsonString, Settings.class);

                try {
                    settings.data = new String(Base64.decodeBase64(settings.data));
                } catch (Exception e) {
                    setError(String.format("base64 data decode error: %s", e.getMessage()));
                }
                System.out.println(settings.toString());

            } catch (JsonSyntaxException | UnsupportedOperationException | IllegalStateException | NullPointerException e) {
                setError("jsonPostInput.parse Exception: " + e.getMessage());
            }
        }
        return settings;
    }
}
