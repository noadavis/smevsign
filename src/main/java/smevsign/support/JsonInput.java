package smevsign.support;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonInput {

    private boolean error = false;
    private String errorDescription = "";
    private String servicePort;
    private String serviceUrl;
    private final List<String> jsonRequestType = new ArrayList<>(List.of("sign_xml", "sign_file", "sign_string", "queue_xml", "ack_xml"));

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
        Settings settings = new Settings();
        if (!this.error) {
            try {
                JsonElement jElement = JsonParser.parseString(jsonString);
                JsonObject jObject = jElement.getAsJsonObject();
                if (jObject.get("request_type") != null) {
                    settings.requestType = jObject.get("request_type").getAsString();
                    if (!jsonRequestType.contains(settings.requestType)) {
                        setError("request_type unknown");
                        return settings;
                    }
                }
                if (jObject.get("data") != null) {
                    try {
                        byte[] dataBytes = Base64.decodeBase64(jObject.get("data").getAsString());
                        settings.data = new String(dataBytes);
                    } catch (Exception e) {
                        setError(String.format("base64 data decode error: %s", e.getMessage()));
                    }
                }
                if (jObject.get("sign_alias") != null) {
                    settings.signAlias = jObject.get("sign_alias").getAsString();
                }

                if (jObject.get("options") != null) {
                    JsonObject options = jObject.get("options").getAsJsonObject();

                    if ("sign_file".equals(settings.requestType)) {

                        if (options.get("path") != null) {
                            settings.options.path = options.get("path").getAsString();
                        }
                        if (options.get("zip_name") != null) {
                            settings.options.zipName = options.get("zip_name").getAsString();
                        }
                        if (options.get("files") != null) {
                            for (JsonElement el : options.get("files").getAsJsonArray()) {
                                settings.options.files.add(el.getAsString());
                            }
                        }
                        if (options.get("signature_detached") != null) {
                            settings.options.signature_detached = options.get("signature_detached").getAsBoolean();
                        }

                    } else {

                        if (options.get("xml_scheme") != null) {
                            settings.options.xmlScheme = options.get("xml_scheme").getAsString();
                        } else {
                            settings.options.xmlScheme = "1.1";
                        }
                        if (options.get("uuid") != null) {
                            settings.options.uuid = options.get("uuid").getAsString();
                        }
                        //Вид xml ["GetRequestRequest", "GetResponseRequest", "SendRequestRequest", "SendResponseRequest", "AckRequest", "AckResponse"]
                        if (options.get("data_type") != null) {
                            settings.options.dataType = options.get("data_type").getAsString();
                        }
                        //Вид подписи ["create", "sign"]
                        if (options.get("sign_type") != null) {
                            settings.options.signType = options.get("sign_type").getAsString();
                        }

                    }
                }
            } catch (JsonSyntaxException | UnsupportedOperationException | IllegalStateException | NullPointerException e) {
                setError("jsonPostInput.parse Exception: " + e.getMessage());
            }
        }
        return settings;
    }
}
