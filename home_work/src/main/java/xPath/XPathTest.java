package xPath;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class XPathTest {
    private static final String XML_URI   = "https://www.w3schools.com/xml/books.xml";


    //Названия книг, выпущенных после 2004 года
//    private static final String EXPRESSION1 = "/bookstore/book[title[@lang='en']]/price";
    //Авторы всех книг, которые написаны на англ(title на англ)
//    private static final String EXPRESSION1 = "/bookstore/book[title[@lang='en']]/author";
    //Все цены книг, написанных на англ(title на англ)
//    private static final String EXPRESSION1 = "/bookstore/book[year>2004]/title";
    //Аттрибуты  category у книг на англ языке
//    private static final String EXPRESSION1 = "/bookstore/book[title[@lang='en']]";
//    private static final String EXPRESSION1 = "/bookstore/book[title[@lang='en']]/@category";
//    //    private static final String EXPRESSION1 = "/bookstore/book[title[@lang='en']]";
    private static final String EXPRESSION1 = "//author";

    public XPathTest() throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try{
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {
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

        try {
            expr = xpath.compile(EXPRESSION1);
        } catch (XPathExpressionException ex) {
            throw new Exception("Can't parse xPath expression " + EXPRESSION1);
        }

        NodeList nodeList;
        try {
            nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            throw new Exception("Can't evaluate exression");
        }

        // Output text of elements
        for( int i = 0 ; i < nodeList.getLength() ; i++ ){
            System.out.println(nodeList.item(i).getTextContent());
        }
    }

    public static void main(String[] args){
        try {
            XPathTest xPathTest = new XPathTest();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }


}
