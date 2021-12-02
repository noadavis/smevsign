package smevsign.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smevsign.cryptopro.SignFile;
import smevsign.cryptopro.SignString;
import smevsign.support.ContainerConfig;
import smevsign.support.JsonInput;
import smevsign.support.ServiceAnswer;
import smevsign.support.Settings;
import smevsign.xml.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


@WebServlet(name = "signServlet", urlPatterns = {"/api/sign"})
public class Api extends HttpServlet {

    final private String className = this.getClass().getSimpleName();
    Log log = LogFactory.getLog(className);
    private final ConfigManager config = ConfigManager.getInstance();

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ServiceAnswer serviceAnswer = new ServiceAnswer();

        String xml = null;
        JsonInput jsonInput = new JsonInput();
        Settings settings = jsonInput.parse(req);
        jsonInput.check(settings);
        if (!jsonInput.isError()) {
            ContainerConfig container = getContainerConfigByAlias(settings.getSignAlias());
            if (container != null) {
                //"sign_xml", "sign_file", "sign_string", "queue_xml", "ack_xml", "digest", "sign_string"
                switch (settings.getRequestType()) {
                    case "sign_xml":
                        Smev3Xml srv = Smev3XmlImpl.getServiceXml(settings, container, config.getRegisteredAlgorithms(), config.getDebug());
                        if (srv == null) setError("Unknown smev scheme", serviceAnswer);
                        else if (srv.isError()) setError(srv.getErrorDescription(), serviceAnswer);
                        else if ("digest".equals(settings.getSignType())) serviceAnswer.digest = srv.getDigests();
                        else xml = srv.getXml();
                        break;
                    case "queue_xml":
                        Smev3Queue queue = Smev3QueueImpl.getQueueXml(settings, container, config.getDebug());
                        if (queue == null) setError("Unknown smev scheme", serviceAnswer);
                        else if (queue.isError()) setError(queue.getErrorDescription(), serviceAnswer);
                        else xml = queue.getXml();
                        break;
                    case "ack_xml":
                        Smev3Ack ack = Smev3AckImpl.getAckXml(true, settings, container, config.getDebug());
                        if (ack == null) setError("Unknown smev scheme", serviceAnswer);
                        else if (ack.isError()) setError(ack.getErrorDescription(), serviceAnswer);
                        else xml = ack.getXml();
                        break;
                    case "sign_string":
                        SignString signString = new SignString(settings, container);
                        if (signString.isError()) setError(signString.getErrorDescription(), serviceAnswer);
                        else serviceAnswer.value = signString.getValue();
                        break;
                    case "sign_file":
                        //todo: before sign with zip - delete old sig files
                        SignFile signFile = new SignFile(settings, container, config.getDebug());
                        if (signFile.isError()) setError(signFile.getErrorDescription(), serviceAnswer);
                        else serviceAnswer.value = signFile.getValue();
                        break;
                    default:
                        setError("request type unknown", serviceAnswer);
                        break;
                }
            } else {
                setError(String.format("sign container config [%s] not found", settings.getSignAlias()), serviceAnswer);
            }
        } else {
            setError(jsonInput.getErrorDescription(), serviceAnswer);
        }
        if (xml != null) {
            serviceAnswer.serviceAnswer = Base64.encodeBase64String(
                    addSoap(xml).getBytes(StandardCharsets.UTF_8)
            );
        } else if (serviceAnswer.isValueExist()) {
            //pass
        } else if (serviceAnswer.isDigestExist()) {
            //pass
        } else if (!serviceAnswer.error) {
            setError("unknown error", serviceAnswer);
        }


        res.setStatus(200);
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, Content-Type");
        res.setHeader("Access-Control-Max-Age", "86400");
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().print(
                getServiceAnswer(serviceAnswer)
        );
    }

    private void setError(String errDesc, ServiceAnswer serviceAnswer) {
        serviceAnswer.serviceAnswer = "";
        serviceAnswer.error = true;
        serviceAnswer.errorDescription = errDesc;
        log.info(errDesc);
    }

    private ContainerConfig getContainerConfigByAlias(String alias) {
        if (config.getJsonConfigContainers().size() > 0) {
            String aliasForSearch = alias;
            if (aliasForSearch == null) {
                aliasForSearch = config.getDefaultAlias();
            }
            for (ContainerConfig cc : config.getJsonConfigContainers()) {
                if (cc.alias.equals(aliasForSearch)) {
                    return cc;
                }
            }
        }
        return null;
    }

    private String getServiceAnswer(ServiceAnswer serviceAnswer) {
        JsonObject jObj = new JsonObject();
        jObj.addProperty("error", serviceAnswer.error);
        jObj.addProperty("error_description", serviceAnswer.errorDescription);
        jObj.addProperty("full_xml", serviceAnswer.serviceAnswer);
        if (serviceAnswer.isDigestExist()) {
            jObj.add("digest", new Gson().toJsonTree(serviceAnswer.digest));
        }
        if (serviceAnswer.isValueExist()) {
            jObj.addProperty("value", serviceAnswer.value);
        }
        return jObj.toString();
    }

    private String addSoap(String xml) {
        StringBuilder sb = new StringBuilder();
        sb.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body>");
        sb.append(xml);
        sb.append("</S:Body></S:Envelope>");
        return sb.toString();
    }

    private String getOpenCertInfo(String openCertB64) {
        byte[] publicKeyBytes = Base64.decodeBase64(openCertB64);
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(publicKeyBytes));
            return "Загружен действующий до " + certificate.getNotAfter() + " сертификат " + certificate.getSubjectDN().getName();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, Content-Type");
        res.setHeader("Access-Control-Max-Age", "86400");
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setStatus(200);
        res.getWriter().print(String.format("{\"name\": \"service_api\", \"answer\": \"GET answer: %s\"}", className));
    }
}