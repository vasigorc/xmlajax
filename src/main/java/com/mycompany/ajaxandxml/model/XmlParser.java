/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Opens existing XML file, parses it and produces Collections objects to send
 * to the view
 *
 * @author vasigorc
 */
public class XmlParser {
    /*
     get the DocumentBuilderFactory object
     */

    private Document doc = null;
    private ArrayList<String> moviesFound;

    //Contructor
    public XmlParser() throws SAXException, IOException,
            ParserConfigurationException {
        /*this.doc = db.parse(inputFile);
         */
        boolean append = true;
        // FileHandler handler = new FileHandler("C:\\Users\\vasigorc\\Documents\\NetBeansProjects\\ajaxandxml\\src\\main\\java\\com\\mycompany\\ajaxandxml\\model\\MyLogFile.log", append);
        //Logger logger = Logger.getLogger("MyLog");
        //logger.addHandler(handler);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        db = dbf.newDocumentBuilder();
        doc = db.parse(new File("C:\\Users\\vasigorc\\Documents\\NetBeansProjects\\ajaxandxml\\src\\main\\java\\com\\mycompany\\ajaxandxml\\model\\simplemovies.xml"));
        doc.getDocumentElement().normalize();
        this.moviesFound = new ArrayList<>();
    }

    /**
     * find and retrieves all movies filmed by the director from
     *
     * @param director
     * @return ArrayList of movie titles
     */
    public ArrayList<String> getMoviesByDirector(String queryDirector) {
        /*
         in case any previous queries were performed, remove everything from
         the list
         */
        moviesFound.clear();
        NodeList actorMovies = doc.getElementsByTagName("movie");
        //loop through each movie in the .xml file
        for (int i = 0; i < actorMovies.getLength(); i++) {
            //grab one 'movie' Element & work with it
            Element movie = (Element) actorMovies.item(i);
            //create a list of all nodes with all actors of this movie
            NodeList absCast = movie.getElementsByTagName("directors");
            for (int j = 0; j < absCast.getLength(); j++) {
                /*
                 * There should only be one Node of actors, but let's double 
                 * check
                 */
                Element actorsEl = (Element) absCast.item(j);
                //this is the actual list of actors within the 'actors'
                NodeList actors = actorsEl.getElementsByTagName("director");
                /*
                 And now we'll loop through all the actors to compare
                 if he's the same as the one we're looking for
                 */
                for (int k = 0; k < actors.getLength(); k++) {
                    Element anActor = (Element) actors.item(k);
                    String actorsName = anActor.getFirstChild().getTextContent()
                            .trim();
                    if (actorsName.equalsIgnoreCase(queryDirector)) {
                        moviesFound.add(movie.getElementsByTagName("title")
                                .item(0).getTextContent().trim());
                    }
                }
            }
        }
        return moviesFound;
    }

    /**
     * finds and retrieves all movies where this actor was starring
     *
     * @param actor - the actor we're looking for
     * @return ArrayList of movie titles
     */
    public ArrayList<String> getMoviesByActor(String queryActor) {
        /*
         in case any previous queries were performed, remove everything from
         the list
         */
        moviesFound.clear();
        NodeList actorMovies = doc.getElementsByTagName("movie");
        //loop through each movie in the .xml file
        for (int i = 0; i < actorMovies.getLength(); i++) {
            //grab one 'movie' Element & work with it
            Element movie = (Element) actorMovies.item(i);
            //create a list of all nodes with all actors of this movie
            NodeList absCast = movie.getElementsByTagName("actors");
            for (int j = 0; j < absCast.getLength(); j++) {
                /*
                 * There should only be one Node of actors, but let's double 
                 * check
                 */
                Element actorsEl = (Element) absCast.item(j);
                //this is the actual list of actors within the 'actors'
                NodeList actors = actorsEl.getElementsByTagName("actor");
                /*
                 And now we'll loop through all the actors to compare
                 if he's the same as the one we're looking for
                 */
                for (int k = 0; k < actors.getLength(); k++) {
                    Element anActor = (Element) actors.item(k);
                    String actorsName = anActor.getFirstChild().getTextContent()
                            .trim();
                    if (actorsName.equalsIgnoreCase(queryActor)) {
                        moviesFound.add(movie.getElementsByTagName("title")
                                .item(0).getTextContent().trim());
                    }
                }
            }
        }
        return moviesFound;
    }
}
