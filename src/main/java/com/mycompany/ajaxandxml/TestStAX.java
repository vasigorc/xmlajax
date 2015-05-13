/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml;

import com.mycompany.ajaxandxml.model.Movie;
import com.mycompany.ajaxandxml.model.StaxParser;
import com.mycompany.ajaxandxml.model.XmlParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;

/**
 *
 * @author vasigorc
 */
@WebServlet(name = "TestStAX", urlPatterns = {"/resultswithstax"})
public class TestStAX extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String usrChoice = request.getParameter("choice");
        String inputValue = request.getParameter("input");
        try (PrintWriter out = response.getWriter()) {
            switch (usrChoice) {
                case "Movie Title":
                    try {
                        this.getMovieInfo(request, response, inputValue);
                    } catch (XMLStreamException ex) {
                        out.println("<h1>XML Stream Exception thrown in the "
                                + "switch" + ex.getMessage() + "</h1>");
                    }
                    break;
                case "Specific Actor":
                    this.getActorMovies(response, inputValue);
                    break;
                case "Specific Director":
                    this.getDirectorMovies(response, inputValue);
                    break;
                default:
                    out.println("<h1>Something went really wrong</h1>");
            }
        }
    }

    protected void getMovieInfo(HttpServletRequest request, HttpServletResponse response, String movieTitle)
            throws IOException, ServletException, XMLStreamException {
        try {
            Movie reqMovie = new StaxParser().getMovieInfo(movieTitle);
            request.setAttribute("selectedMovie", movieTitle);
            request.setAttribute("movieInfo", reqMovie);
            String url = "movieinfo.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (IOException ex) {
            try (PrintWriter writer = response.getWriter();) {
                writer.print("<h3>" + movieTitle + " threw IOException - " + ex.getMessage() + "</h3>");
            }
        }
    }

    protected void getActorMovies(HttpServletResponse response, String actor)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            ArrayList<String> moviesByActor = new ArrayList<>();
            try {
                moviesByActor = new StaxParser().getActorMovies(actor);
            } catch (FileNotFoundException | XMLStreamException e) {
                out.println("<h3>You entered actor " + actor + "\n"
                        + "Something went wrong with StAX Parser</h3>");
            }
            if (moviesByActor.isEmpty()) {
                out.println("<h2>" + actor + " didn't play in any upcomming movies.</h2>");
            } else {
                out.println("<h3>" + actor + " played in the following movies: </h3><ul class=" + "list-inline" + ">");
                for (String movie : moviesByActor) {
                    out.println("<li>" + movie.toString() + "</li>");
                }
                out.println("</ul>");
            }
        }
    }

    protected void getDirectorMovies(HttpServletResponse response,
            String director) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            ArrayList<String> moviesByDirector = new ArrayList<>();
            try {
                moviesByDirector = new StaxParser().getDirectorMovies(director);
            } catch (FileNotFoundException | XMLStreamException e) {
                out.println("<h3>You entered director " + director + "\n"
                        + "Something went wrong with StAX Parser</h3>");
            }
            if (moviesByDirector.isEmpty()) {
                out.println("<h2>" + director + " didn't shoot any recent"
                        + " movies.</h2>");
            } else {

                out.println("<h3>" + director + " shoot following movies: "
                        + "</h3><ul class=" + "list-inline" + ">");
                for (String movie : moviesByDirector) {
                    out.println("<li>" + movie.toString() + "</li>");
                }
                out.println("</ul>");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
