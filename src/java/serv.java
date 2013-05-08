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
import org.openrdf.repository.RepositoryException;

import java.lang.Math.*;
/**
 *
 * @author rosskaplan
 */
//@WebServlet(name = "serv", urlPatterns = {"/serv"})
public class serv extends HttpServlet {

    public ServletOutputStream out;
    //static {
    //  org.openjena.atlas.logging.Log.setLog4j("jena-log4j.properties");
    //}
    static String linkString = "";
    static String linkString2 = "";
    static String artist = "";
    static String uname = "";
    static String song = "";
    static String qArtist = "";
    static RDFNode labelArt;
    static boolean  checkArtist = false;
    static boolean  checkSong = false;

    /*    public static void out(OutputStream out, ResultSet qresults, Prologue prologue)
     {        TextOutput tFmt = new TextOutput(prologue) ;
     tFmt.format(out, qresults) ;
     }
    

     public static String asText(ResultSet qresults) {
     ByteArrayOutputStream arr = new ByteArrayOutputStream();
     out(arr, qresults);
     try {
     return new String(arr.toByteArray(), "UTF-8");
     } catch (UnsupportedEncodingException e) {
     return null;
     }
     }
     */
    public static void out(OutputStream out, ResultSet qresults) {
        out(out, qresults, (PrefixMapping) null);
    }

    public static void out(OutputStream out, ResultSet qresults, PrefixMapping pmap) {
        TextOutput tFmt = new TextOutput(pmap);
        tFmt.format(out, qresults);
    }
    
    public static boolean checkArtistExist(String a)
    {   char[] charArray = a.toCharArray();
        String newString = "";


            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i]=='\''){
                    newString += '\\';
                    newString += charArray[i];
                }
                else {
                    newString += charArray[i];
                }
                    
            }
            
            newString = newString.substring(0,newString.length()-3);
            
        String userLikesArtists =
                "PREFIX :<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX mm-2:<http://musicbrainz.org/mm/mm-2.1#>"
                + "PREFIX owl:<http://www.w3.org/2002/07/owl#>"
                + "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX MusicMachine:<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "SELECT ?a" + "\n"
                + "WHERE {" + "\n"
                + "?user MusicMachine:username '" + uname + "'." + "\n"
                + "?a MusicMachine:artistLikedBy ?user." + "\n"
                + "?a rdfs:label '" + newString +"'."
                + "\n"
                + "}";

        Query userArtistQuery = QueryFactory.create(userLikesArtists);
        QueryExecution qexecArtist = QueryExecutionFactory.sparqlService("http://localhost:8080/openrdf-sesame/repositories/Music", userArtistQuery);
        ResultSet resultsUserArtist = qexecArtist.execSelect();

        if (resultsUserArtist.hasNext()) {
            return true;
        }
        else
            return false;
        
    }
    
    public static boolean checkSongExist(String s)
    {   char[] charArray = s.toCharArray();
        String newString = "";


            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i]=='\''){
                    newString += '\\';
                    newString += charArray[i];
                }
                else {
                    newString += charArray[i];
                }
                    
            }
        String userLikesSongs =
                "PREFIX :<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX mm-2:<http://musicbrainz.org/mm/mm-2.1#>"
                + "PREFIX owl:<http://www.w3.org/2002/07/owl#>"
                + "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX MusicMachine:<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "SELECT ?s" + "\n"
                + "WHERE {" + "\n"
                + "?user MusicMachine:username '" + uname + "'." + "\n"
                + "?s MusicMachine:songLikedBy ?user." + "\n"
                + "?s rdfs:label '" + newString +"'."
                + "\n"
                + "}";

        Query userSongQuery = QueryFactory.create(userLikesSongs);
        QueryExecution qexecSong = QueryExecutionFactory.sparqlService("http://localhost:8080/openrdf-sesame/repositories/Music", userSongQuery);
        ResultSet resultsUserSong = qexecSong.execSelect();

        if (resultsUserSong.hasNext()) {
            return true;
        }
        else
            return false;
        
    }

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

        uname = request.getParameter("username").toString();
        String artist = "";
        String song = "";
        String genre = "";
        String suggA = "";
        String suggOutA = "";
        String suggS = "";
        String suggOutS = "";

        List<String> list = new ArrayList<String>();
        List<String> likeArtistList = new ArrayList<String>();
        List<String> likeGenreList = new ArrayList<String>();
        
        int rand = (int)Math.random()*50+1;

        String userLikesArtists =
                "PREFIX :<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX mm-2:<http://musicbrainz.org/mm/mm-2.1#>"
                + "PREFIX owl:<http://www.w3.org/2002/07/owl#>"
                + "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX MusicMachine:<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "SELECT ?l " + "\n"
                + "WHERE {" + "\n"
                + "?user MusicMachine:username '" + uname + "'." + "\n"
                + "?a rdf:type MusicMachine:Artist." + "\n"
                + "?a MusicMachine:artistLikedBy ?user." + "\n"
                + "?a rdfs:label ?l." + "\n"
                + "}";

        Query userArtistQuery = QueryFactory.create(userLikesArtists);
        QueryExecution qexecArtist = QueryExecutionFactory.sparqlService("http://localhost:8080/openrdf-sesame/repositories/Music", userArtistQuery);
        ResultSet resultsUserArtist = qexecArtist.execSelect();


        while (resultsUserArtist.hasNext()) {
            QuerySolution lsoln = resultsUserArtist.next();
            RDFNode label = lsoln.get("?l");
            likeArtistList.add(lsoln.get("?l").toString());
            String labelStr = label.toString();
            
            
            char[] charArray = labelStr.toCharArray();
            String newString = "";
            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i]=='\''){
                    newString += '\\';
                    newString += charArray[i];
                }
                else {
                    newString += charArray[i];
                }
                    
            }
            //BEGIN CODE
            String imgQueryString=
                "PREFIX :<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX mm-2:<http://musicbrainz.org/mm/mm-2.1#>"
                + "PREFIX owl:<http://www.w3.org/2002/07/owl#>"
                + "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX MusicMachine:<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "SELECT ?img " + "\n"
                + "WHERE {" + "\n"
                + "?a rdfs:label '" + newString + "'." + "\n"
                + "?a rdf:type MusicMachine:Artist." + "\n"
                + "?a MusicMachine:imageURL ?img." + "\n"
                + "}"
                + "LIMIT 1";

                Query artistImgQuery = QueryFactory.create(imgQueryString);
                QueryExecution qexecImg = QueryExecutionFactory.sparqlService("http://localhost:8080/openrdf-sesame/repositories/Music", artistImgQuery);
                ResultSet resultsArtistImg = qexecImg.execSelect();
            
            //END CODE
            String imgURLStr = "";
            if (resultsArtistImg.hasNext()){
                QuerySolution lsolnImg = resultsArtistImg.next();
                RDFNode img = lsolnImg.get("?img");
                imgURLStr = img.toString();
                if(imgURLStr.equals("")){
                    artist += "<div class='float'><a class='delA' href='javascript:void(0)'>" + label + "</a></br><img src='noImg.gif'></br></div>";
                }
                else{
                    artist += "<div class='float'><a class='delA' href='javascript:void(0)'>" + label + "</a></br><img src='"+imgURLStr+"'></br></div>";
                }
            }
            else{
                artist += "<div class='float'><a class='delA' href='javascript:void(0)'>" + label + "</a></br><img src='noImg.gif'></br></div>";
            }
            imgURLStr = "";
            
        }

        String userLikesSongs =
                "PREFIX :<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX mm-2:<http://musicbrainz.org/mm/mm-2.1#>"
                + "PREFIX owl:<http://www.w3.org/2002/07/owl#>"
                + "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX MusicMachine:<http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#>"
                + "SELECT ?l" + "\n"
                + "WHERE {" + "\n"
                + "?user MusicMachine:username '" + uname + "'." + "\n"
                + "?a rdf:type MusicMachine:Song." + "\n"
                + "?a MusicMachine:songLikedBy ?user." + "\n"
                + "?a rdfs:label ?l." + "\n"
                + "}";

        Query userSongQuery = QueryFactory.create(userLikesSongs);
        QueryExecution qexecSong = QueryExecutionFactory.sparqlService("http://localhost:8080/openrdf-sesame/repositories/Music", userSongQuery);
        ResultSet resultsUserSong = qexecSong.execSelect();

        while (resultsUserSong.hasNext()) {
            QuerySolution lsoln = resultsUserSong.next();
            RDFNode label = lsoln.get("?l");

            song += "<a class='delS' href='javascript:void(0)'>" + label + "</a>";
        }
        
        
 // ADDED CODE  
        
        
        for (String likeArtistStr : likeArtistList)
        {
            char[] charArray = likeArtistStr.toCharArray();
            String likeArtist = "";


            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i]=='\''){
                    likeArtist += '\\';
                    likeArtist += charArray[i];
                }
                else {
                    likeArtist += charArray[i];
                }
                    
            }
            suggA = "";
            suggS = "";
            //sparql query takes in first liked artist and outs members of that band (if there are any)
            String queryStr =
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
                    + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                    + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                    + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                    + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                    + "PREFIX : <http://dbpedia.org/resource/>\n"
                    + "PREFIX dbpedia2: <http://dbpedia.org/property/>\n"
                    + "PREFIX dbpedia: <http://dbpedia.org/>\n"
                    + "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
                    + "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
                    + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"
                    + "SELECT ?z\n"
                    + "WHERE {\n"
                    + "?a foaf:name '"+likeArtist+"'@en.\n"
                    + "OPTIONAL {\n"
                    + "?a rdf:type dbpedia-owl:Band\n"
                    + "}\n"
                    + "OPTIONAL {\n"
                    + "?a rdf:type dbpedia-owl:MusicalArtist\n"
                    + "}\n"
                    + "?x dbpedia-owl:associatedMusicalArtist ?a.\n"
                    + "OPTIONAL {\n"
                    + "?x dbpedia-owl:associatedBand ?a\n"
                    + "}\n"
                    + "?x foaf:name ?z.\n"
                    + "}";
                   // + "LIMIT 1";


        Query query = QueryFactory.create(queryStr);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://DBpedia.org/sparql", query);

        ResultSet results = qexec.execSelect();
        //ResultSetFormatter.out(System.out, results, query); 
        
        while (suggA.equals("") && results.hasNext()) {
            //String r= results.toString();
            //System.out.println(r);
            QuerySolution lsoln = results.next();
            RDFNode label = lsoln.get("?z");
            String labelStr = label.toString();
            checkArtist = checkArtistExist(label.toString());
            if (!checkArtist){
                suggA += "<a href='javascript:void(0)' class='addA'>" + labelStr.substring(0,labelStr.length()-3) + "</a>";
                suggOutA += suggA;
            }
        }
 
          
                 qexec.close();  
        
        
        String userLikesGenres =
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
                    + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                    + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                    + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                    + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                    + "PREFIX : <http://dbpedia.org/resource/>\n"
                    + "PREFIX dbpedia2: <http://dbpedia.org/property/>\n"
                    + "PREFIX dbpedia: <http://dbpedia.org/>\n"
                    + "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
                    + "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
                    + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"
                    + "SELECT ?z\n"
                    + "WHERE {\n"
                    + "?a foaf:name '"+likeArtist+"'@en.\n"
                    + "OPTIONAL {\n"
                    + "?a rdf:type dbpedia-owl:Band\n"
                    + "}\n"
                    + "OPTIONAL {\n"
                    + "?a rdf:type dbpedia-owl:MusicalArtist\n"
                    + "}\n"
                    + "?g rdf:type dbpedia-owl:MusicGenre.\n"
                    + "?a dbpprop:genre ?g.\n"
                    + "?g foaf:name ?z.\n"
                    + "}\n"
                    + "LIMIT 1";

        Query userGenreQuery = QueryFactory.create(userLikesGenres);
        QueryExecution qexecGenre = QueryExecutionFactory.sparqlService("http://DBpedia.org/sparql", userGenreQuery);
        ResultSet resultsUserGenre = qexecGenre.execSelect();

        while (resultsUserGenre.hasNext()) {
            QuerySolution lsoln = resultsUserGenre.next();
            RDFNode label = lsoln.get("?z");
            String labelStr = label.toString();
            likeGenreList.add(labelStr.substring(0,labelStr.length()-3));
            
        }
        //HashSet hs = new HashSet();
        //hs.addAll(likeGenreList);
        //likeGenreList.clear();
        //likeGenreList.addAll(hs);
        
     }
        Set<String> s = new LinkedHashSet<String>(likeGenreList);
        
        for (String likeGenreStr : s){
            genre += "<a class='delG' href='javascript:void(0)'>" + likeGenreStr  + "</a>";
            
            String queryStrS =
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
                    + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                    + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                    + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                    + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                    + "PREFIX : <http://dbpedia.org/resource/>\n"
                    + "PREFIX dbpedia2: <http://dbpedia.org/property/>\n"
                    + "PREFIX dbpedia: <http://dbpedia.org/>\n"
                    + "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n"
                    + "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
                    + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"
                    + "SELECT ?songname\n"
                    + "WHERE {\n"
                    + "?g rdf:type dbpedia-owl:MusicGenre.\n"
                    + "?s rdf:type dbpedia-owl:Song.\n"
                    + "?g foaf:name '"+likeGenreStr+"'@en.\n"
                    + "?a dbpprop:genre ?g.\n"
                    + "OPTIONAL {\n"
                    + "?a rdf:type dbpedia-owl:Band\n"
                    + "}\n"
                    + "OPTIONAL {\n"
                    + "?a rdf:type dbpedia-owl:MusicalArtist.\n"
                    + "}\n"                   
                    + "?s foaf:name ?songname\n"
                    + "}\n"
                    + "OFFSET "+ rand + "\n"
                    + "LIMIT 1";


            Query queryS = QueryFactory.create(queryStrS);
            QueryExecution qexecS = QueryExecutionFactory.sparqlService("http://DBpedia.org/sparql", queryS);

            ResultSet resultsS = qexecS.execSelect();
            //ResultSetFormatter.out(System.out, results, query); 
            RDFNode title = null;
            String titleStr = "";
            while (resultsS.hasNext()&& title==null) {
                //String r= results.toString();
                //System.out.println(r);
                QuerySolution lsoln = resultsS.next();
                title = lsoln.get("?songname");
                titleStr = title.toString();

                checkSong = checkSongExist(title.toString());
                if (!checkSong){
                    suggOutS += "<a href='javascript:void(0)' class='addS'>" + titleStr.substring(0,titleStr.length()-3) + "</a>";

                }
                title=null;
            } 
            
            
                     qexecS.close();  
        }
        
        
        
// END OF ADDED CODE
        

        list.add(artist);
        list.add(song);
        list.add(genre);
        list.add(suggOutA);
        list.add(suggOutS);


        String json = new Gson().toJson(list);
        response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        response.getWriter().write(json);

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
