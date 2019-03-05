package xPath.homeWorkXPath;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.Scanner;

public class XPathExample2 {
    private static final String XML_URI = "https://www.w3schools.com/xml/books.xml";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new Exception("Can't crate DocumentBuilder");
        }
        Document doc;
        try {
            doc = builder.parse(XML_URI);
        } catch (IOException ex) {
            throw new Exception("Can't get XML by URL " + XML_URI);
        } catch (SAXException ex) {
            throw new Exception("Can't read downloaded XML.");
        }
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr;

        while (true) {
            System.out.println("Введите expression:");
            String expression = sc.next();
            try {
                expr = xpath.compile(expression);
            } catch (XPathExpressionException ex) {
                throw new Exception("Can't parse expression expression " + expression);
            }
            NodeList nodeList;
            nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (nodeList.getLength() == 0) {
                System.out.println("No results found for your request");
            }
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                int type = node.getNodeType();
                switch (type) {
                    case (Node.ELEMENT_NODE):
                        System.out.println("Тип - элемент; имя - " + node.getNodeName());
                        System.out.println("\n");
                        NamedNodeMap nodeAttributes = node.getAttributes();
                        if (nodeAttributes.getLength() > 0) {
                            System.out.println("Атрибуты элемента :");
                            for (int j = 0; j < nodeAttributes.getLength(); j++) {
                                System.out.println("Имя: " + nodeAttributes.item(j).getNodeName());
                                System.out.println("Значение: " + nodeAttributes.item(j).getNodeValue());
                                System.out.println("------------------------------------");
                            }
                        }
                        break;

                    case (Node.ATTRIBUTE_NODE):
                        System.out.println("Тип - атрибут; имя - " + node.getNodeName() + "; Значение: " + node.getNodeValue());
                        break;
                    case (Node.TEXT_NODE):
                        System.out.println(node.getTextContent());
                        break;
                    default:throw new Exception("Sorry! But in this app we can work only with Text, attributes and nodes");

                }


            }

        }
    }
}


