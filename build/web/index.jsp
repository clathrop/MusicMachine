<%-- 
    Document   : index
    Created on : Apr 29, 2013, 2:26:52 PM
    Author     : rosskaplan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles.css" />
        <script type="text/javascript" src="jquery-1.8.1.min.js"></script>
        <script type="text/javascript" src="loginPage.js"></script>
        <title>The Music Machine</title>
    </head>
    <body>
        <div class="main">
            <h1>Welcome to The Music Machine</h1>
            <form id="loginFrm" method="LINK" action="profile.jsp">
                <%--<form method="LINK" action="serv" method="post">--%>
            User: <input name="username" type="text" id="user"/>
             <!-- Favorite Artists: <input name="artists" type="text" id="user"/>
            Favorite Songs: <input name="songs" type="text" id="user"/>
            Favorite Genres: <input name="genres" type="text" id="user"/>
         -->
            
         <div id="result" style="color:red;"></div>
            </form>
            <button id="enter">Log In!</button>
            <button id="register">Register</button>
            
            <div class="register">
                Favorite Artists: <input name="artists" type="text" id="artist"/>
                Favorite Songs: <input name="songs" type="text" id="song"/>
                Favorite Genres: <input name="genres" type="text" id="genre"/>
            <button id="sendReg">Enter</button>
            </div>
        </div>
       
    </body>
</html>

