package com.haloandrei.socket.server.repository;


import com.haloandrei.socket.common.domain.Movie;
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

public class XmlMovieRepository{
//    public static void main(String[] args) {
//        try {
//            List<Movie> books = loadData();
//            books.forEach(System.out::println);
//        } catch (ParserConfigurationException | IOException | SAXException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            saveMovie(new Movie("action","Some action movie",4));
//        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("hello");
//    }

    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }

    private static Movie createMovieFromElement(Element movieElement) {
        Movie movie = new Movie();

        String category = movieElement.getAttribute("type");
        movie.setType(category);

//        Node titleNode = bookElement.getElementsByTagName("title").item(0);
//        String title = titleNode.getTextContent();
//        book.setTitle(title);
        movie.setName(getTextFromTagName(movieElement, "name"));
        movie.setRating(Integer.parseInt(getTextFromTagName(movieElement, "rating")));
        movie.setPrice(Integer.parseInt(getTextFromTagName(movieElement, "price")));

        return movie;
    }

    private static List<Movie> loadData() throws ParserConfigurationException, IOException, SAXException {

//        DocumentBuilder bd = new DocumentBuilder();
        List<Movie> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/moviestore.xml");

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        return IntStream
                .range(0, children.getLength())
                .mapToObj(index -> children.item(index))
                .filter(node -> node instanceof Element)
                .map(node -> createMovieFromElement((Element) node))
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

    public static void saveMovie(Movie movie) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("moviestore.xml");

        Element root = document.getDocumentElement();
        Node movieNode = movieToNode(movie, document);
        root.appendChild(movieNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult(new File("moviestore.xml")));
    }

    public static Node movieToNode(Movie movie, Document document) {
        Element movieElement = document.createElement("movie");
        movieElement.setAttribute("category", movie.getType());

//        Element titleElement = document.createElement("title");
//        titleElement.setTextContent(book.getTitle());
//        bookElement.appendChild(titleElement);
        appendChildWithTextToNode(document, movieElement, "name", movie.getName());
        appendChildWithTextToNode(document, movieElement, "rating", String.valueOf(movie.getRating()));
        appendChildWithTextToNode(document, movieElement, "rating", String.valueOf(movie.getRating()));
        return movieElement;
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
