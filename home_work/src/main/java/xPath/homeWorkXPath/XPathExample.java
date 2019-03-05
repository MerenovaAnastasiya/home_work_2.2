package xPath.homeWorkXPath;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XPathExample {
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
            expression.replaceAll("\\s", "");
            try {
                expr = xpath.compile(expression);
            } catch (XPathExpressionException ex) {
                throw new Exception("Can't parse expression expression " + expression);
            }
            int res = numberReg(expression) | booleanReg(expression) | stringReg(expression);
            System.out.println(res);
            try {
                switch (res) {
                    case 1:
                        Number numberRes = (Number) expr.evaluate(doc, XPathConstants.NUMBER);
                        System.out.println(numberRes);
                        break;
                    case 2:
                        Boolean booleanRes = (Boolean) expr.evaluate(doc, XPathConstants.BOOLEAN);
                        System.out.println(booleanRes);
                        break;
                    case 3:
                        String stringRes = (String) expr.evaluate(doc, XPathConstants.STRING);
                        System.out.println(stringRes);
                        break;

                    default:
                        NodeList nodeList;
                        nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                        if(nodeList.getLength() == 0) {
                            System.out.println("No results found for your request");
                        }
                        for(int i = 0; i < nodeList.getLength(); i++) {
                            System.out.println(nodeList.item(i).getNodeType()+" " + nodeList.item(i).getNodeValue());
                        }
                }
            } catch (XPathExpressionException ex) {
                System.out.println("Invalid expression");
            }
        }
    }

    public static int numberReg(String expression) {
        Pattern pattern = Pattern.compile("^(last|position|count|string-length|number|sum|floor|ceiling|round).*");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.matches()) {
            return 1;
        }
        return 0;

    }

    public static int booleanReg(String expression) {

        Pattern pattern = Pattern.compile("^(starts-with|contains|boolean|not|true|false|lang).*");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.matches()) {
            return 2;
        }
        return 0;

    }

    public static int stringReg(String expression) {
        Pattern pattern = Pattern.compile("^(local-name|namespace-uri|name|string|concat|substring-before|substring-after|substring|normalize-space|translate).*");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.matches()) {
            return 3;
        }
        return 0;
    }

}
