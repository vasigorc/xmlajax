/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml;

import com.mycompany.ajaxandxml.model.XmlParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "MainController", urlPatterns = "/xmlhandler")
public class MainController extends HttpServlet {    
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
    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String usrChoice = request.getParameter("choice");
        String inputValue = request.getParameter("input");

        switch (usrChoice) {
            case "Movie Title": /*some method*/
                break;
            case "Specific Actor": this.findByActor(request, response, 
                    inputValue);
                break;
            case "Specific Director":/*some method*/
                break;
            default:
                throw new AssertionError();
        }
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<h1>Servlet MainController at " 
                    + request.getContextPath() + "</h1>");
        } finally {
            out.close();
        }
    }

    protected void findByActor(HttpServletRequest request, 
            HttpServletResponse response, String actor) 
            throws ServletException, IOException {
        try (PrintWriter writer = response.getWriter();) {
            XmlParser aParser = new XmlParser();
            ArrayList<String> starredMovies = aParser.getMoviesByActor(actor);
            if (starredMovies.size() < 1) {
                writer.append("<h1>No films could have been found for "
                        + actor + " </h1>");
            } else {
                writer.append("<ul class=\"list-inline\">");
                for (String movie : starredMovies) {
                    writer.append("<li>" + movie);
                    writer.append("</li>");
                }
                writer.append("</ul>");
            }
        } catch (SAXException | ParserConfigurationException| IOException ex) {
            try (PrintWriter errorWriter = response.getWriter();) {
                errorWriter.append("<h1>The Parser 'didn't work':-(</h1>");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click 
    //on the + sign on the left to edit the code.">
    /**
     * 
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, 
    HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request, 
    HttpServletResponse response)
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
