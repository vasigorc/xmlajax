/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.model;

import org.joda.time.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.stream.*;

/**
 *
 * @author vasigorc
 */
public class StaxParser {

    private final ArrayList<String> moviesFound;
    private XMLStreamReader reader;

    public StaxParser() throws FileNotFoundException, XMLStreamException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream input = new FileInputStream(new File("C:\\Users\\vasigorc\\Documents\\NetBeansProjects\\ajaxandxml\\src\\main\\java\\com\\mycompany\\ajaxandxml\\model\\simplemovies.xml"));
        this.reader = inputFactory.createXMLStreamReader(input);
        this.moviesFound = new ArrayList<>();
    }

    public Movie getMovie(String movieTitle) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equalsIgnoreCase("title")) {
                int titleEvent = reader.next();
                if (titleEvent == XMLStreamConstants.CHARACTERS && reader.getText().trim().equalsIgnoreCase(movieTitle.trim())) {
                    //instantiate Movie obj
                    Movie mov = new Movie();
                    mov.setTitle(reader.getText().trim());
                    /*
                     here we might get problems while calling the method
                     * try removing the first condition if it doesn't work
                     */
                    while (reader.next() != XMLStreamConstants.END_ELEMENT && !reader.getLocalName().equalsIgnoreCase("movie")) {
                        int goodMovie = reader.next();
                        //fill in Movie fields
                        if (goodMovie == XMLStreamConstants.START_ELEMENT) {
                            switch (reader.getLocalName()) {
                                case "nopasses":
                                    reader.next();
                                    if (reader.getText().trim().equalsIgnoreCase("false")) {
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
                                default:
                                    break;
                            }
                        }
                    }
                    //here return statement
                }
            }
        }
        return null;
    }
}
