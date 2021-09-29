package smevsign.smev;

import com.fasterxml.uuid.Generators;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import smevsign.smev.signature.Signature;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.GregorianCalendar;
import java.util.UUID;


public class Utils {
    public static String generateUuid1() {
        UUID uuid1 = Generators.timeBasedGenerator().generate();
        return uuid1.toString();
    }

    public static String generateUuid4() {
        UUID uuid4 = Generators.randomBasedGenerator().generate();
        return uuid4.toString();
    }

    public static XMLGregorianCalendar getXMLGregorianCalendarNow() {
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object convertXmlToJAXBObject(String xml, String className) {
        try {
            Class<?> classByName = null;
            try {
                classByName = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(classByName);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xml);
            return unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertSignatureObjectToXml(Signature signature) {
        try {
            JAXBContext context = JAXBContext.newInstance(Signature.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal(signature, sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertJAXBObjectToXml(Object serviceRequest, String className, String prefix) {
        try {
            Class<?> classByName = null;
            try {
                classByName = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            JAXBContext context = JAXBContext.newInstance(classByName);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            if (prefix != null) {
                //change prefix name: only for custom nodes and jaxb classes without package-info.java
                marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
                    @Override
                    public String getPreferredPrefix(String arg0, String arg1, boolean arg2) {
                        return prefix;
                    }
                });
            }
            StringWriter sw = new StringWriter();
            marshaller.marshal(serviceRequest, sw);
            return sw.toString();
        } catch (JAXBException e) {
            System.out.print(e.getMessage());
        }
        return null;
    }

    public static String getStringFromNode(Node node) {
        try {
            StringWriter sw = new StringWriter();
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
            return sw.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        }
        catch(TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Element getElementFromClass(Object target) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            JAXB.marshal(target, new DOMResult(doc));
            return doc.getDocumentElement();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static Element getElementFromXmlString(String xml) {
//        try {
//            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document doc = db.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
//            return doc.getDocumentElement();
//        } catch (ParserConfigurationException | SAXException | IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static Element getElementFromXmlString(String text) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
            return doc.getDocumentElement();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
