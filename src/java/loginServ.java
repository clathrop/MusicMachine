/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.lang.Object;
import com.hp.hpl.jena.sparql.core.Prologue;
import java.io.IOException;
import java.io.PrintWriter;
import com.hp.hpl.jena.sparql.resultset.TextOutput;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletInputStream;
import com.hp.hpl.jena.shared.PrefixMapping;
import javax.servlet.ServletException;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Scanner;
import java.io.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.vocabulary.RDF;
import java.util.*;


/**
 *
 * @author Elyssa
 */
public class loginServ extends HttpServlet {

    static String uname = "";
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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet loginServ</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginServ at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
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
        
        String out = "";
        uname = request.getParameter("username").toString();
        
        String getUser =
                "PREFIX :<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX mm-2:<http://musicbrainz.org/mm/mm-2.1#>"
                + "PREFIX owl:<http://www.w3.org/2002/07/owl#>"
                + "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX MusicMachine:<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "SELECT ?user" + "\n"
                + "WHERE {" + "\n"
                + "?user MusicMachine:username '" + uname + "'." + "\n"
                + "}";

        Query userQuery = QueryFactory.create(getUser);
        QueryExecution qexecUser = QueryExecutionFactory.sparqlService("http://localhost:8080/openrdf-sesame/repositories/Music", userQuery);
        ResultSet resultsUser = qexecUser.execSelect();
      
        if (resultsUser.hasNext())
           out="true";
        else
           out="false";
        
        response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        response.getWriter().write(out);  
        
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
