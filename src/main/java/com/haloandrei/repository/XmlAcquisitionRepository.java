package com.haloandrei.repository;

import com.haloandrei.domain.Acquisition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XmlAcquisitionRepository {

        private static String getTextFromTagName(Element parentElement, String tagName) {
            Node node = parentElement.getElementsByTagName(tagName).item(0);
            return node.getTextContent();
        }

        private static Acquisition createAcquisitionFromElement(Element acquisitionElement) throws ParseException {
            Acquisition acquisition = new Acquisition();

            Date date = acquisition.getDateFormat().parse(acquisitionElement.getAttribute("date"));
            acquisition.setDate(date);

//        Node titleNode = bookElement.getElementsByTagName("title").item(0);
//        String title = titleNode.getTextContent();
//        book.setTitle(title);
            //client.setName(getTextFromTagName(clientElement, "name"));
            acquisition.setPriceBought(Double.parseDouble(getTextFromTagName(acquisitionElement, "priceBought")));
            // client.setPrice(Float.parseFloat(getTextFromTagName(clientElement, "price")));

            return acquisition;
        }

        private static List<Acquisition> loadData() throws ParserConfigurationException, IOException, SAXException {

//        DocumentBuilder bd = new DocumentBuilder();
            List<Acquisition> result = new ArrayList<>();

            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse("./data/acquisitionstore.xml");

            Element root = document.getDocumentElement();

            NodeList children = root.getChildNodes();
            return IntStream
                    .range(0, children.getLength())
                    .mapToObj(index -> children.item(index))
                    .filter(node -> node instanceof Element)
                    .map(node -> {
                        try {
                            return createAcquisitionFromElement((Element) node);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                                return null;
                            }
                    )
                    .collect(Collectors.toList());

//        for (int index = 0; index < children.getLength(); index++){
//            Node bookNode = children.item(index);
//            if (bookNode instanceof Element) {
//                Book newBook = createBookFromElement((Element) bookNode);
//                result.add(newBook);
//            }
//        }
//        return result;
        }

        public static void saveacquisition(Acquisition acquisition) throws ParserConfigurationException, IOException, SAXException, TransformerException {
            System.out.println("da");
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse("acquisitionstore.xml");

            Element root = document.getDocumentElement();
            Node acquisitionNode = acquisitionToNode(acquisition, document);
            root.appendChild(acquisitionNode);

            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult(new File("acquisitionstore.xml")));
        }

        public static Node acquisitionToNode(Acquisition acquisition, Document document) {
            Element acquisitionElement = document.createElement("acquisition");
            acquisitionElement.setAttribute("date", acquisition.getFromatedDate());

//        Element titleElement = document.createElement("title");
//        titleElement.setTextContent(book.getTitle());
//        bookElement.appendChild(titleElement);
            appendChildWithTextToNode(document, acquisitionElement, "priceBought", String.valueOf(acquisition.getPriceBought()));
            appendChildWithTextToNode(document, acquisitionElement, "id", String.valueOf(acquisition.getId()));

            return acquisitionElement;
        }

        private static void appendChildWithTextToNode(Document document,
                                                      Node parentNode,
                                                      String tagName,
                                                      String textContent) {
            Element element = document.createElement(tagName);
            element.setTextContent(textContent);
            parentNode.appendChild(element);
        }
    }

