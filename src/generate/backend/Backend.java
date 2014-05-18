package generate.backend;

import org.w3c.dom.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sobolev-a on 28.04.2014.
 */
public class Backend {
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private TransformerFactory transformerFactory;
    private Transformer transformer;
    private DOMSource source;
    private Document document;
    private File file;
    private StreamResult streamResult;
    private URL url;

    public Backend() throws Exception {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        url = getClass().getClassLoader().getResource("psswd.xml");
        file = new File(url.getFile());
        streamResult = new StreamResult(file);
    }

    public List<String> parseXML() {
        List<String> list = new ArrayList();
        try {
            document = documentBuilder.parse(file);
            NodeList nList = document.getElementsByTagName("service");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    list.add(
                      eElement.getElementsByTagName("name").item(0).getTextContent() + "," +
                      eElement.getElementsByTagName("password").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            Frame frame = new JFrame("Title");
            JOptionPane.showMessageDialog(frame,
                    e.getMessage(),
                    "XML error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return list;
    }

    public void addService(String serviceName, String passwordService) throws Exception {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        document = documentBuilderFactory.newDocumentBuilder().parse(file);
        Element service = document.createElement("service");
        Element root = document.getDocumentElement();
        root.appendChild(service);
        Element name = document.createElement("name");
        name.appendChild(document.createTextNode(serviceName));
        service.appendChild(name);
        Element password = document.createElement("password");
        password.appendChild(document.createTextNode(passwordService));
        service.appendChild(password);

        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        source = new DOMSource(document);
        transformer.transform(source, streamResult);
    }

    public void removeService(String serviceName) throws Exception {
        DocumentBuilderFactory documentBuilderFactoryFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilderFactory = documentBuilderFactoryFactory.newDocumentBuilder();
        document = documentBuilderFactory.parse(file);
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(String.format("//services/service[name[text()='%s']]", serviceName));
        Node node = (Node) expression.evaluate(document, XPathConstants.NODE);
        node.getParentNode().removeChild(node);

        transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer();
        source = new DOMSource(document);
        transformer.transform(source, streamResult);
    }
}