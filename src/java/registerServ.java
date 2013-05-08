/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.lang.Object;
import java.io.IOException;
import java.io.PrintWriter;
//import com.hp.hpl.jena.sparql.resultset.TextOutput;
import javax.servlet.ServletOutputStream;
//import com.hp.hpl.jena.shared.PrefixMapping;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.openrdf.repository.Repository;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.RepositoryException;
import java.util.List;
import org.openrdf.OpenRDFException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.model.*;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;

/**
 *
 * @author Elyssa
 */
public class registerServ extends HttpServlet {
    static String uname = "";
    static String artists = "";
    static String songs = "";
    //static String genres = "";
    static String artistURI = "";
    static String songURI = "";
    //static String genreURI = "";
    static String imgURL = "";
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
        uname = request.getParameter("uname").toString();
        artists = request.getParameter("artists").toString();
        artistURI = artists.replaceAll("\\s","");
        songs = request.getParameter("songs").toString();
        songURI = songs.replaceAll("\\s","");
        //genres = request.getParameter("genres").toString();
        //genreURI = genres.replaceAll("\\s","");
        String out = "";
        
        if (uname==""){
            out="Please enter a username.";
            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
            response.getWriter().write(out);
        }
        
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
      
        if (resultsUser.hasNext()){
            out="Username already exists";
            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
            response.getWriter().write(out);
        }
           
        else{        
        
            String sesameServer = "http://localhost:8080/openrdf-sesame";
            String repositoryID = "Music";

            Repository repo = new HTTPRepository(sesameServer, repositoryID);
            //String surname = "Mutt";

            try{
                repo.initialize();
            }catch(RepositoryException e){

            }

            ValueFactory f = repo.getValueFactory();
    //
    //
    //// create some resources and literals to make statements out of
            URI newUser = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#"+uname);
            URI User = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#User");
            URI username = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#username");
            Literal newName = f.createLiteral(uname);
    //
            try {
                RepositoryConnection con = repo.getConnection();
    //
                try {
    //      

                    con.add(newUser, RDF.TYPE, User);

                    con.add(newUser, username, newName);
                }
                finally {
                    con.close();
                }
            }
            catch (OpenRDFException e) {
    //   // handle exception
            }
            if(!artists.equals("")){
                
                String imgQueryString =
                "PREFIX map: <file:/home/moustaki/work/motools/musicbrainz/d2r-server-0.4/mbz_mapping_raw.n3#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX event: <http://purl.org/NET/c4dm/event.owl#>"
                + "PREFIX rel: <http://purl.org/vocab/relationship/>"
                + "PREFIX lingvoj: <http://www.lingvoj.org/ontology#>"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX tags: <http://www.holygoat.co.uk/owl/redwood/0.1/tags/>"
                + "PREFIX db: <http://dbtune.org/musicbrainz/resource/>"
                + "PREFIX geo: <http://www.geonames.org/ontology#>"
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
                + "PREFIX bio: <http://purl.org/vocab/bio/0.1/>"
                + "PREFIX mo: <http://purl.org/ontology/mo/>"
                + "PREFIX vocab: <http://dbtune.org/musicbrainz/resource/vocab/>"
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
                + "PREFIX mbz: <http://purl.org/ontology/mbz#>"
                + "SELECT ?image" + "\n"
                + "WHERE {" + "\n"
                + "?a rdfs:label '" + artists + "'." + "\n"
                + "?a rdf:type mo:MusicArtist." + "\n"
                + "?album foaf:maker ?a." + "\n"
                + "?album rdf:type mo:Record." + "\n"
                + "?album mo:image ?image." + "\n"
                + "}"
                + "LIMIT 1";

                Query artistImgQuery = QueryFactory.create(imgQueryString);
                QueryExecution qexecImg = QueryExecutionFactory.sparqlService("http://dbtune.org/musicbrainz/sparql", artistImgQuery);
                ResultSet resultsArtistImg = qexecImg.execSelect();

                while (resultsArtistImg.hasNext()) {
                    QuerySolution lsoln = resultsArtistImg.next();
                    RDFNode image = lsoln.get("?image");
                    String strImg = image.toString();
                    imgURL = strImg;
                }
        
                URI newArtist = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#"+artistURI);
                URI Artist = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#Artist");
                URI artistLikedBy = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#artistLikedBy");
                URI newImgURL = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#imageURL");
                Literal newArtistName = f.createLiteral(artists);
                Literal newImg = f.createLiteral(imgURL);
        //
                try {
                    RepositoryConnection con = repo.getConnection();
        //
                    try {
        //      

                        con.add(newArtist, RDF.TYPE, Artist);

                        con.add(newArtist, RDFS.LABEL, newArtistName);

                        con.add(newArtist, artistLikedBy, newUser);
                        
                        con.add(newArtist, newImgURL, newImg);
                    }
                    finally {
                        con.close();
                    }
                }
                catch (OpenRDFException e) {
        //   // handle exception
                }
            }
            if(songs != ""){
                URI newSong = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#"+songURI);
                URI Song = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#Song");
                URI songLikedBy = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#songLikedBy");
                Literal newSongName = f.createLiteral(songs);
        //
                try {
                    RepositoryConnection con = repo.getConnection();
        //
                    try {
        //      

                        con.add(newSong, RDF.TYPE, Song);

                        con.add(newSong, RDFS.LABEL, newSongName);

                        con.add(newSong, songLikedBy, newUser);
                    }
                    finally {
                        con.close();
                    }
                }
                catch (OpenRDFException e) {
        //   // handle exception
                }
            }
           /* if(genres != ""){
                URI newGenre = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#"+genreURI);
                URI Genre = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#Genre");
                URI genreLikedBy = f.createURI("http://www.semanticweb.org/ontologies/2013/3/MusicMachine.owl#genreLikedBy");
                Literal newGenreName = f.createLiteral(genres);
        //
                try {
                    RepositoryConnection con = repo.getConnection();
        //
                    try {
        //      

                        con.add(newGenre, RDF.TYPE, Genre);

                        con.add(newGenre, RDFS.LABEL, newGenreName);

                        con.add(newGenre, genreLikedBy, newUser);
                    }
                    finally {
                        con.close();
                    }
                }
                catch (OpenRDFException e) {
        //   // handle exception
                }
            }*/
            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
            response.getWriter().write(out);
            
        }
        
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
