/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.rest;

import com.mycompany.ajaxandxml.model.StaxParserForWebServices;
import com.mycompany.ajaxandxml.model.Movie;
import com.mycompany.ajaxandxml.ws.*;
import java.util.ArrayList;
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
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RestfulMovies
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }
}
