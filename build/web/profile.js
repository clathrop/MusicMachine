function newTuple (upd, type,value,username)
{
    $.get("updateServ?upd="+upd+"&type="+type+"&value="+value+"&uname="+username, function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
        location.reload();
    });
}


$(document).ready(function(){
    var locate = window.location
    document.welcome.n.value = locate
    var str = document.welcome.n.value
    theleft = str.indexOf("=") + 1;
    username = str.substring(theleft, str.length);
    var value = "";
    $("#name").text("Welcome "+ username);
    
    $(".hide").hide();
    
    $.get("serv?username="+username, function(responseJson) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
        $.each(responseJson, function(index, item) { // Iterate over the JSON array.
            if (index==0)
                $('#a').append(item); 
            else if (index==1)
                $('#s').append(item); 
            else if (index==2)
                $('#g').append(item);
            else if (index==3)
                $('#Asug').append(item);
            else
                $('#Ssug').append(item);
        });         
    });
    
    $(function () {
        $("#addArtist").click(function(){
            $("#newArtist").show();
            $("#entArtist").show();
            $("#addArtist").hide();
        });
    });
    $(function () {
        $("#entArtist").click(function(){
            newTuple("add","artist",$("#newArtist").val(),username);
        });
    });
    $(function () {
        $("#addSong").click(function(){
            $("#newSong").show();
            $("#entSong").show();
            $("#addSong").hide();
        });
    });
    $(function () {
        $("#entSong").click(function(){
            newTuple("add","song",$("#newSong").val(),username);
        });
    });
    
    $(function () {
        $('.addA').live("click",function(){
            newTuple("add","artist",$(this).html(),username);
          })
    }); 
    $(function () {
        $('.addS').live("click",function(){
            newTuple("add","song",$(this).html(),username);
          })
    }); 
    $(function () {
        $('.delA').live("click",function(){
            //newTuple("add","song",$(this).html(),username);
            $("#delArtist").show()
            value = $(this).html();
          })
    }); 
    $(function () {
        $('#delArtist').live("click",function(){
            newTuple("delete","artist",value,username);
          })
    }); 
    $(function () {
        $('.delS').live("click",function(){
            //newTuple("add","song",$(this).html(),username);
            $("#delSong").show()
            value = $(this).html();
          })
    }); 
    $(function () {
        $('#delSong').live("click",function(){
            newTuple("delete","song",value,username);
          })
    }); 
    $(function () {
        $('.delG').live("click",function(){
            //newTuple("add","song",$(this).html(),username);
            $("#delGenre").show()
            value = $(this).html();
          })
    }); 
    $(function () {
        $('#delGenre').live("click",function(){
            newTuple("delete","genre",value,username);
          })
    }); 
    
    

});


