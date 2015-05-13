/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.ws;

import com.mycompany.ajaxandxml.model.Movie;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import com.mycompany.ajaxandxml.model.StaxParserForWebServices;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.bind.annotation.*;

/**
 *
 * @author vasigorc
 */
@WebService(name = "MovieBill", serviceName = "MovieBillService")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieBill {
    private ArrayList<String> moviesList;
//    @XmlElement(name="parser")
//    private StaxParserForWebServices parser;

    //constructor
    public MovieBill(){
        moviesList = new ArrayList<>();
    }

    /**
     * @return movie that was retrieved from XML
     */
    @WebMethod(operationName = "findMovieInfo")
    public Movie findMovieInfo(@WebParam(name = "title") String title) 
            throws IOException {
        Movie mov = new StaxParserForWebServices().getMovieInfo(title);
        return mov;
    }
    
    @WebMethod(operationName="findActorMovies")
    public ArrayList<String> findActorMovies
            (@WebParam (name="actorName")String actorName)throws IOException{
        moviesList.clear();
        moviesList = new StaxParserForWebServices().getActorMovies(actorName);
        return moviesList;
    }
    
    @WebMethod(operationName="findDirectorMovies")
    public ArrayList<String> findDirectorMovies(@WebParam(name="directorName")
    String directorName)throws IOException{
        moviesList.clear();
        moviesList = new StaxParserForWebServices().getDirectorMovies(directorName);
        return moviesList;
    }

}
