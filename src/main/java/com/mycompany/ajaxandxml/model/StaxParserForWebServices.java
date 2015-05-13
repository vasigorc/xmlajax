/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

/**
 *
 * @author vasigorc
 */
public class StaxParserForWebServices {
    private final ArrayList<String> moviesFound;
    private XMLStreamReader reader;

    public StaxParserForWebServices() throws IOException{
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream input = new FileInputStream(new File("C:\\Users\\vasigorc\\Documents\\NetBeansProjects\\ajaxandxml\\src\\main\\java\\com\\mycompany\\ajaxandxml\\model\\simplemovies.xml"));
        try {
            this.reader = inputFactory.createXMLStreamReader(input);
        } catch (XMLStreamException ex) {
            Logger.getLogger(StaxParserForWebServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.moviesFound = new ArrayList<>();
    }

    public Movie getMovieInfo(String movieTitle) throws IOException {
        try {
            boolean append = true;
            FileHandler handler = new FileHandler("C:\\Users\\vasigorc\\Documents\\NetBeansProjects\\ajaxandxml\\src\\main\\java\\com\\mycompany\\ajaxandxml\\model\\MyLogFile.log", append);
            Logger logger = Logger.getLogger("MyLog");
            logger.addHandler(handler);
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equalsIgnoreCase("title")) {
                    int titleEvent = reader.next();
                    if (titleEvent == XMLStreamConstants.CHARACTERS && reader.getText().trim().equalsIgnoreCase(movieTitle.trim())) {
                        /*
                         instantiate Movie obj and collections for filling its 
                         instance variables
                         */
                        Movie mov = new Movie();
                        ArrayList<LocalTime> showTimes = new ArrayList<>();
                        ArrayList<String> actors = new ArrayList<>();
                        ArrayList<String> directors = new ArrayList<>();
                        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
                        List<String> producers = new ArrayList<>();
                        List<String> writers = new ArrayList<>();
                        List<String> studios = new ArrayList<>();

                        mov.setTitle(reader.getText().trim());

                        /*
                         here we might get problems while calling the method
                         * try removing the first condition if it doesn't work
                         */
                        boolean movieEnded = false;
                        while (reader.hasNext() && movieEnded == false) {
                            int goodMovie = reader.next();
                            if (goodMovie == XMLStreamConstants.END_ELEMENT) {
                                if (reader.getLocalName().equalsIgnoreCase("movie")) {
                                    movieEnded = true;
                                }
                            }
                            //fill in Movie fields
                            if (goodMovie == XMLStreamConstants.START_ELEMENT) {
                                switch (reader.getLocalName()) {
                                    case "nopasses":
                                        reader.next();
                                        String decision = reader.getText().trim();
                                        if (decision.equalsIgnoreCase("false")) {
                                            mov.setNoPasses(false);
                                        } else {
                                            mov.setNoPasses(true);
                                        }
                                        break;
                                    case "duration":
                                        reader.next();
                                        if (reader.isCharacters()) {
                                            Period aPeriod = Movie.getParser().parsePeriod(reader.getText().trim());
                                            mov.setDuration(aPeriod);
                                        } else {
                                            mov.setDuration(Period.ZERO);
                                        }
                                        break;
                                    case "genre":
                                        reader.next();
                                        if (reader.isCharacters()) {
                                            mov.setGenre(reader.getText().trim());
                                        } else {
                                            mov.setGenre(null);
                                        }
                                        break;
                                    case "rank":
                                        reader.next();
                                        if (reader.isCharacters()) {
                                            mov.setRank(reader.getText().trim());
                                        } else {
                                            mov.setRank(null);
                                        }
                                        break;
                                    case "showtime":
                                        reader.next();
                                        LocalTime lt = LocalTime.parse(reader.getText().trim(), Movie.getTimeFormatter());
                                        showTimes.add(lt);
                                        break;
                                    case "released":
                                        reader.next();
                                        if (reader.isCharacters()) {
                                            mov.setReleased(LocalDate.parse(reader.getText().trim(), Movie.getDateFormatter()));
                                        } else {
                                            mov.setReleased(null);
                                        }
                                        break;
                                    case "actor":
                                        reader.next();
                                        String anActor = reader.getText().trim();
                                        actors.add(anActor);
                                        break;
                                    case "director":
                                        reader.next();
                                        String aDirector = reader.getText().trim();
                                        directors.add(aDirector);
                                        break;
                                    case "producer":
                                        reader.next();
                                        String aProducer = reader.getText().trim();
                                        producers.add(aProducer);
                                        break;
                                    case "writer":
                                        reader.next();
                                        if (reader.isCharacters()) {
                                            String aWriter = reader.getText().trim();
                                            writers.add(aWriter);
                                        }
                                        break;
                                    case "studio":
                                        reader.next();
                                        String aStudio = reader.getText().trim();
                                        studios.add(aStudio);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        /*
                         some  of the properties must be set after the loop has
                         finished. Because of the nature of StAX we cannot count
                         number of "children elements"
                         */
                        mov.setShowingon(showTimes.toArray(new LocalTime[showTimes.size()]));
                        if (actors.size() < 1) {
                            actors.add("No actors in this movie");
                        }
                        mov.setActors(actors.toArray(new String[actors.size()]));
                        mov.setDirectors(directors.toArray(new String[directors.size()]));
                        mov.setProducers(producers.toArray(new String[producers.size()]));
                        if (writers.size() < 1) {
                            writers.add("No writers in this movie");
                        }
                        mov.setWriters(writers.toArray(new String[writers.size()]));
                        mov.setStudios(studios.toArray(new String[studios.size()]));
                        //here return statement
                        return mov;
                    }
                }
            }
            return null;
        } catch (XMLStreamException ex) {
            return null;
        }
    }

    public ArrayList<String> getActorMovies(String actorName){
        try {
            String currentTitle = "";
            while (reader.hasNext()) {
                int event = reader.next();
                //get title name & store it into "current title"
                if (event == XMLStreamConstants.START_ELEMENT
                        && reader.getLocalName().equalsIgnoreCase("title")) {
                    reader.next();
                    currentTitle = reader.getText().trim();
                } else if (event == XMLStreamConstants.START_ELEMENT
                        && reader.getLocalName().equalsIgnoreCase("actor")) {
                    //check whether queried actor is within this movie
                    reader.next();
                    if (reader.isCharacters() && reader.getText().trim().equalsIgnoreCase(actorName)) {
                        if (currentTitle.length() > 0) {
                            moviesFound.add(currentTitle);
                        }
                    }
                }
            }
            return moviesFound;
        } catch (XMLStreamException ex) {
            moviesFound.add("XMLStreamException was thrown on the server");
            return moviesFound;
        }
    }

    public ArrayList<String> getDirectorMovies(String directorName){
        try {
            String currentTitle = "";
            while (reader.hasNext()) {
                int event = reader.next();
                //get title name & store it into "current title"
                if (event == XMLStreamConstants.START_ELEMENT
                        && reader.getLocalName().equalsIgnoreCase("title")) {
                    reader.next();
                    currentTitle = reader.getText().trim();
                } else if (event == XMLStreamConstants.START_ELEMENT
                        && reader.getLocalName().equalsIgnoreCase("director")) {
                    //check whether queried actor is within this movie
                    reader.next();
                    if (reader.isCharacters() && reader.getText().trim().equalsIgnoreCase(directorName)) {
                        if (currentTitle.length() > 0)
                            moviesFound.add(currentTitle);
                    }
                }
            }
            return moviesFound;
        } catch (XMLStreamException ex) {
            moviesFound.add("XMLStreamException was thrown on the server");
            return moviesFound;
        }
    }
}
