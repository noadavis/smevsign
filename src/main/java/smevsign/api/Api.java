package smevsign.api;

import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smevsign.cryptopro.SignFile;
import smevsign.support.ContainerConfig;
import smevsign.support.JsonInput;
import smevsign.support.ServiceAnswer;
import smevsign.support.Settings;
import smevsign.xml.AbstractXmlBuilder;

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
    private final boolean debug = config.getDebug();

    private String getXmlFromBuilder(AbstractXmlBuilder builder, ServiceAnswer serviceAnswer) {
        if (builder.getError()) {
            setError(builder.getErrorDescription(), serviceAnswer);
        } else {
            return builder.getXml();
        }
        return null;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ServiceAnswer serviceAnswer = new ServiceAnswer();

        String xml = null;
        JsonInput jsonInput = new JsonInput();
        Settings settings = jsonInput.parse(req);
        jsonInput.check(settings);
        if (!jsonInput.isError()) {
            ContainerConfig container = getContainerConfigByAlias(settings.getSignAlias());
            if (container != null) {
                //"sign_xml", "sign_file", "sign_string", "queue_xml", "ack_xml"
                switch (settings.getRequestType()) {
                    case "sign_xml":
                        switch (settings.getXmlScheme()) {
                            case "1.1":
                                smevsign.xml.v1_1.ServiceXml srvXml = new smevsign.xml.v1_1.ServiceXml(settings, container, debug);
                                xml = getXmlFromBuilder(srvXml, serviceAnswer);
                                break;
                            case "1.2":
                                setError("sign_xml 1.2 in development", serviceAnswer);
                                break;
                            case "1.3":
                                setError("sign_xml 1.3 in development", serviceAnswer);
                                break;
                            default:
                                setError("unknown xml scheme", serviceAnswer);
                        }
                        break;
                    case "queue_xml":
                        switch (settings.getXmlScheme()) {
                            case "1.1":
                                smevsign.xml.v1_1.QueueXml queueXml1 = new smevsign.xml.v1_1.QueueXml(settings, container, debug);
                                xml = getXmlFromBuilder(queueXml1, serviceAnswer);
                                break;
                            case "1.2":
                                smevsign.xml.v1_2.QueueXml queueXml2 = new smevsign.xml.v1_2.QueueXml(settings, container, debug);
                                xml = getXmlFromBuilder(queueXml2, serviceAnswer);
                                break;
                            case "1.3":
                                smevsign.xml.v1_3.QueueXml queueXml3 = new smevsign.xml.v1_3.QueueXml(settings, container, debug);
                                xml = getXmlFromBuilder(queueXml3, serviceAnswer);
                                break;
                            default:
                                setError("unknown xml scheme", serviceAnswer);
                        }
                        break;
                    case "ack_xml":
                        switch (settings.getXmlScheme()) {
                            case "1.1":
                                smevsign.xml.v1_1.AckXml ackXml1 = new smevsign.xml.v1_1.AckXml(true, settings, container, debug);
                                xml = getXmlFromBuilder(ackXml1, serviceAnswer);
                                break;
                            case "1.2":
                                smevsign.xml.v1_2.AckXml ackXml2 = new smevsign.xml.v1_2.AckXml(true, settings, container, debug);
                                xml = getXmlFromBuilder(ackXml2, serviceAnswer);
                                break;
                            case "1.3":
                                smevsign.xml.v1_3.AckXml ackXml3 = new smevsign.xml.v1_3.AckXml(true, settings, container, debug);
                                xml = getXmlFromBuilder(ackXml3, serviceAnswer);
                                break;
                            default:
                                setError("unknown xml scheme", serviceAnswer);
                        }
                        break;
                    case "sign_file":
                        //todo: before sign with zip - delete old sig files
                        SignFile signFile = new SignFile(settings, container, debug);
                        xml = getXmlFromBuilder(signFile, serviceAnswer);
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
        } else if (!serviceAnswer.error) {
            setError("unknown error: xml is null", serviceAnswer);
        }


        if (serviceAnswer.error) {
            res.setStatus(400);
        } else {
            res.setStatus(200);
        }

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
        jObj.addProperty("data", "");
        jObj.addProperty("signature", "");
        jObj.addProperty("full_xml", serviceAnswer.serviceAnswer);
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
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setStatus(200);
        res.getWriter().print(String.format("{\"name\": \"service_api\", \"answer\": \"GET answer: %s\"}", className));

    }
}