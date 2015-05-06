/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml;

import com.mycompany.ajaxandxml.model.Movie;
import com.mycompany.ajaxandxml.model.XmlParser;
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
import org.xml.sax.SAXException;

/**
 *
 * @author vasigorc
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/resultsreceiver"})
public class TestServlet extends HttpServlet {

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
                    this.getMovieInfo(request, response, inputValue);
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

    protected void getDirectorMovies(HttpServletResponse response, String director)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            ArrayList<String> moviesByDirector = new ArrayList<>();
            try {
                XmlParser aParser = new XmlParser();
                moviesByDirector = aParser.getMoviesByDirector(director);
            } catch (SAXException | ParserConfigurationException ex) {
                out.println("<h1>You entered director " + director + "\n"
                        + "Something went wrong with XML Parser</h1>");
            }
            if (moviesByDirector.isEmpty()) {
                out.println("<h3>" + director + " didn't direct any upcomming movies.</h3>");
            } else {
                out.println("<h3>" + director + " shoot the following movies: </h3><ul class=" + "list-inline" + ">");
                for (String movie : moviesByDirector) {
                    out.println("<li>" + movie.toString() + "</li>");
                }
                out.println("</ul>");
            }

        }
    }

    protected void getActorMovies(HttpServletResponse response, String actor)
            throws IOException {
        try (PrintWriter out = response.getWriter()) {
            ArrayList<String> moviesByActor = new ArrayList<>();
            try {
                XmlParser aParser = new XmlParser();
                moviesByActor = aParser.getMoviesByActor(actor);
            } catch (SAXException | ParserConfigurationException ex) {
                out.println("<h1>You entered actor " + actor + "\n"
                        + "Something went wrong with XML Parser</h1>");
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

    protected void getMovieInfo(HttpServletRequest request, HttpServletResponse response, String movieTitle)
            throws IOException, ServletException {
        //        try (PrintWriter out = response.getWriter()) {
        //            out.println("<h1>We're watching " + movieTitle + "</h1>");
        //        }
        try {
//            boolean append = true;
//            FileHandler handler = new FileHandler("C:\\Users\\vasigorc\\Documents\\NetBeansProjects\\ajaxandxml\\src\\main\\java\\com\\mycompany\\ajaxandxml\\model\\MyLogFile.log", append);
//            Logger logger = Logger.getLogger("MyLog");
//            logger.addHandler(handler);            
            Movie reqMovie = new XmlParser().getMovieInfo(movieTitle);            
            request.setAttribute("selectedMovie", movieTitle);
            request.setAttribute("movieInfo", reqMovie);
            String url = "movieinfo.jsp";
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);              
        } catch (SAXException ex) {
            try (PrintWriter writer = response.getWriter();){
                writer.print("<h3>"+movieTitle+" threw SAXException - "+ex.getMessage()+"</h3>");
            }
        }catch(IOException ex){
            try (PrintWriter writer = response.getWriter();){
                writer.print("<h3>"+movieTitle+" threw IOException - "+ex.getMessage()+"</h3>");
            }
        }catch(ParserConfigurationException ex){
            try (PrintWriter writer = response.getWriter();){
                writer.print("<h3>"+movieTitle+" threw ParserConfigurationException - "+ex.getMessage()+"</h3>");
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
