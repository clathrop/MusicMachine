

$(document).ready(function(){
    $(function () {
        $("#enter").click(function(){
            username = $("#user").val();
            $.get("loginServ?username="+username, function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                if(responseText=="true")
                    $("#loginFrm").submit();
                else
                    $("#result").html("User does not exist.");
            });
                        
        });
    });
    
    $(function () {
        $("#register").click(function(){
            $(".register").show();
        });
    });
    
    $(function () {
        $("#sendReg").click(function(){
            username = $("#user").val();
            artist = $("#artist").val();
            song = $("#song").val();
            genre = $("#genre").val();
            $.get("registerServ?uname="+username+"&artists="+artist+"&songs="+song+"&genres="+genre, function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                if(responseText == "")
                    $("#loginFrm").submit();
                else
                    $("#result").html(responseText);    
            });
        });
    });
   
    
    $(".register").hide();
    
});



