/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.rest;

import com.mycompany.ajaxandxml.model.StaxParserForWebServices;
import com.mycompany.ajaxandxml.model.Movie;
import com.mycompany.ajaxandxml.ws.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;

/**
 * REST Web Service
 *
 * @author vasigorc
 */
@Path("/services")
@RequestScoped
public class RestfulMovies {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RestfulMovies
     */
    public RestfulMovies() {
    }

    /**
     * Retrieves representation of an instance of com.mycompany.ajaxandxml.rest.RestfulMovies
     * @param name
     * @return an instance of java.lang.String
     */
    @GET
    @Path("actors/{name}")
    @Produces("application/xml")
    public ArrayList<String> getActorMovies(@PathParam("name") String name) {
        try {
            //TODO return proper representation object
            ArrayList<String> movies = new StaxParserForWebServices().getActorMovies(name);
            return movies;
        } catch (IOException ex) {
            Logger.getLogger(RestfulMovies.class.getName()).log(Level.INFO, ex.getMessage());
        }
        return null;
    }
    
    @GET
    @Path("directors/{name}")
    @Produces("application/xml")
    public ArrayList<String> getDirectorMovies(@PathParam("name") String name){
        try{
            ArrayList<String> movies = new StaxParserForWebServices().getDirectorMovies(name);
            return movies;
        }catch(IOException ex){
            Logger.getLogger(RestfulMovies.class.getName()).log(Level.INFO, ex.getMessage());
        }
        return null;
    }
    
    @GET
    @Path("movies/{name}")
    @Produces("application/xml")
    public Movie getMovieInfo(@PathParam("name") String name){
        try {
            Movie aMovie = new StaxParserForWebServices().getMovieInfo(name);
            return aMovie;            
        } catch (IOException ex) {
            Logger.getLogger(RestfulMovies.class.getName()).log(Level.INFO, ex.getMessage());
        }
        return null;
    }    
}
