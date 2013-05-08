<%-- 
    Document   : profile
    Created on : Apr 29, 2013, 2:49:54 PM
    Author     : Elyssa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles.css" />
        <script type="text/javascript" src="jquery-1.8.1.min.js"></script>
        <script type="text/javascript" src="profile.js"></script>
        <title>The Music Machine</title>
    </head>
    <body id="profile">
        <FORM NAME="welcome">
        <INPUT TYPE="hidden" id="n">
        </FORM>
        <div id="content">
            <h1 id="name"></h1>
            <fieldset id="artists">
                <legend>Artists You Like</legend> 
                <button id="addArtist">+</button>
                <input type="text" id="newArtist" class="hide"/>
                <button id="entArtist" class="hide">+</button>
                <a href='javascript:void(0)' id="delArtist" class="hide">Delete?</a>
                <div id="a"></div>
            </fieldset>
            <fieldset id="songs">
                <legend>Songs You Like</legend>
                <button id="addSong">+</button>
                <input type="text" id="newSong" class="hide"/>
                <button id="entSong" class="hide">+</button>
                <a href='javascript:void(0)' id="delSong" class="hide">Delete?</a>
                <div id="s"></div>
            </fieldset>
            <fieldset id="genres">
                <legend>Genres You Have Liked</legend>
                <div id="g"></div>
            </fieldset>
            <fieldset id="Asuggestions">
                <legend>Artist Suggestions</legend>
                <div id="Asug"></div>
            </fieldset>
            <fieldset id="Ssuggestions">
                <legend>Song Suggestions</legend>
                <div id="Ssug"></div>
            </fieldset>
        </body>
</html>
