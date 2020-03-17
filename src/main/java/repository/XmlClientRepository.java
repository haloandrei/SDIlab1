package repository;

import domain.Client;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XmlClientRepository {
    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }

    private static Client createclientFromElement(Element clientElement) {
        Client client = new Client();

        String name = clientElement.getAttribute("name");
        client.setName(name);

//        Node titleNode = bookElement.getElementsByTagName("title").item(0);
//        String title = titleNode.getTextContent();
//        book.setTitle(title);
        //client.setName(getTextFromTagName(clientElement, "name"));
        client.setMoneySpent(Integer.parseInt(getTextFromTagName(clientElement, "moneySpent")));
        // client.setPrice(Float.parseFloat(getTextFromTagName(clientElement, "price")));

        return client;
    }

    private static List<Client> loadData() throws ParserConfigurationException, IOException, SAXException {

//        DocumentBuilder bd = new DocumentBuilder();
        List<Client> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/clientstore.xml");

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createclientFromElement((Element) node))
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

    public static void saveclient(Client client) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/clientstore.xml");

        Element root = document.getDocumentElement();
        Node clientNode = clientToNode(client, document);
        root.appendChild(clientNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File("./data/bookstore2.xml")));
    }

    public static Node clientToNode(Client client, Document document) {
        Element clientElement = document.createElement("client");
        clientElement.setAttribute("name", client.getName());

//        Element titleElement = document.createElement("title");
//        titleElement.setTextContent(book.getTitle());
//        bookElement.appendChild(titleElement);
        appendChildWithTextToNode(document, clientElement, "moneySpent", String.valueOf(client.getMoneySpent()));
        client.getRented_movies().stream().forEach(x -> appendChildWithTextToNode(document, clientElement, "movieRented", x));

        return clientElement;
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
