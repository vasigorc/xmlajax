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
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

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
    private final ArrayList<String> moviesFound;

    //Contructor
    public XmlParser() throws SAXException, IOException,
            ParserConfigurationException {
        
//        boolean append = true;
//        FileHandler handler = new FileHandler("C:\\Users\\vasigorc\\Documents\\NetBeansProjects\\ajaxandxml\\src\\main\\java\\com\\mycompany\\ajaxandxml\\model\\MyLogFile.log", append);
//        Logger logger = Logger.getLogger("MyLog");
//        logger.addHandler(handler);
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
     * @param queryDirector
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
     * @param queryActor - the actor we're looking for
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

    /**
     * @param movieTitle
     * @return
     */    
    public Movie getMovieInfo(String movieTitle) throws IOException {
        /*
         in case any previous queries were performed, remove everything from
         the list
         */
//        boolean append = true;
//        FileHandler handler = new FileHandler("C:\\Users\\vasigorc\\Documents\\NetBeansProjects\\ajaxandxml\\src\\main\\java\\com\\mycompany\\ajaxandxml\\model\\MyLogFile.log", append);
//        Logger logger = Logger.getLogger("MyLog");
//        logger.addHandler(handler);
//        logger.info("Movies selected");
        Movie mov;        
        moviesFound.clear();
        //loop through all the movies to find the movie that we need
        NodeList movies = doc.getElementsByTagName("movie");
        for (int i = 0; i < movies.getLength(); i++) {
            /*
             grab each movie title to do the subsequent comparison
             */
            Element movie = (Element) movies.item(i);
            if (movie.getElementsByTagName("title").item(0).getTextContent().trim().equalsIgnoreCase(movieTitle)) {
                //code for dealing with the movie found event                
                //instantiate Movie class and set instance variables
                mov = new Movie();
                //set title
                mov.setTitle(movie.getElementsByTagName("title").item(0).getTextContent().trim());
                //set nopasses
                if (movie.getElementsByTagName("nopasses").item(0).getTextContent().trim().equalsIgnoreCase("false")) {
                    mov.setNoPasses(false);
                } else {
                    mov.setNoPasses(true);
                }
                //set duration
                if (movie.getElementsByTagName("duration").item(0).hasChildNodes()) {
                    //we won't do the validation of duration, it's the job of xsd
                    Period aPeriod = Movie.getParser().parsePeriod(movie.getElementsByTagName("duration").item(0).getTextContent().trim());
                    mov.setDuration(aPeriod);
                } else {
                    mov.setDuration(Period.ZERO);
                }
                //set genre
                if (movie.getElementsByTagName("genre").item(0).hasChildNodes()) {
                    mov.setGenre(movie.getElementsByTagName("genre").item(0).getTextContent().trim());
                } else {
                    mov.setGenre(null);
                }
                //set rank
                if (movie.getElementsByTagName("rank").item(0).hasChildNodes()) {
                    mov.setRank(movie.getElementsByTagName("rank").item(0).getTextContent().trim());
                } else {
                    mov.setRank(null);
                }                
                //set showingon
                NodeList showingonNl = movie.getElementsByTagName("showingon");
                //however, we need to iterate through "showtimes", thus->
                Element showingonEl = (Element) showingonNl.item(0);
                NodeList showTimes = showingonEl.getElementsByTagName("showtime");
                LocalTime[] ltArray = new LocalTime[showTimes.getLength()];
                for (int j = 0; j < ltArray.length; j++) {
                    Element aShowTime = (Element)showTimes.item(j);
                    //using Movie's static getTimeFormatter() method to format
                    LocalTime lt = LocalTime.parse(aShowTime.getFirstChild().getTextContent().trim(), Movie.getTimeFormatter());
                    ltArray[j] = lt;
                }
                mov.setShowingon(ltArray);
                //set released
                if (movie.getElementsByTagName("released").item(0).hasChildNodes()) {
                    //using Movie's static getDateFormatter() method to format
                    mov.setReleased(LocalDate.parse(movie.getElementsByTagName("released").item(0).getTextContent().trim(), Movie.getDateFormatter()));
                } else {
                    mov.setReleased(null);
                }
                //set actors
                String[]objActors;
                if (movie.getElementsByTagName("actors").item(0).hasChildNodes()) {
                    //get total number of actors with a NodeList
                    Element actorsEl = (Element)movie.getElementsByTagName("actors").item(0);
                    NodeList actors = actorsEl.getElementsByTagName("actor");
                    objActors = new String[actors.getLength()];
                    for (int j = 0; j < objActors.length; j++) {
                        objActors[j]=actors.item(j).getFirstChild().getTextContent().trim();
                    }                    
                }else{
                    objActors = new String[1];
                    objActors[0]= "now actors in this movie";                    
                }
                mov.setActors(objActors);
                //set directors
                String[]objDirectors;
                Element directorsEl = (Element)movie.getElementsByTagName("directors").item(0);
                NodeList directors = directorsEl.getElementsByTagName("director");
                objDirectors = new String[directors.getLength()];
                for (int j = 0; j < objDirectors.length; j++) {
                    objDirectors[j] = directors.item(j).getFirstChild().getTextContent().trim();
                }
                mov.setDirectors(objDirectors);
                //set producers
                String[]objProducers;
                Element producersEl = (Element)movie.getElementsByTagName("producers").item(0);
                NodeList producers = producersEl.getElementsByTagName("producer");
                objProducers = new String[producers.getLength()];
                for (int j = 0; j < objProducers.length; j++) {
                    objProducers[j] = producers.item(j).getFirstChild().getTextContent().trim();
                }
                mov.setProducers(objProducers);
                //set writers
                String[]objWriters;
                if (movie.getElementsByTagName("writers").item(0).hasChildNodes()) {
                    //get total number of writers with a NodeList object
                    Element writersEl = (Element)movie.getElementsByTagName("writers").item(0);
                    NodeList writers = writersEl.getElementsByTagName("writer");
                    objWriters = new String[writers.getLength()];
                    for (int j = 0; j < objWriters.length; j++) {
                        objWriters[j]  = writers.item(j).getFirstChild().getTextContent().trim();                        
                    }
                }else{
                    objWriters=new String[1];
                    objWriters[1]="No writers for this movie";
                }
                mov.setWriters(objWriters);
                //set studios
                String[]objStudios;
                Element studiosEl = (Element)movie.getElementsByTagName("studios").item(0);
                NodeList studios = studiosEl.getElementsByTagName("studio");
                objStudios = new String[studios.getLength()];
                for (int j = 0; j < objStudios.length; j++) {
                    objStudios[j] = studios.item(j).getFirstChild().getTextContent().trim();
                }
                mov.setStudios(objStudios);
                return mov;
            }
        }
        return null;
    }
}
