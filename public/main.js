/**
 * Created by halleyfroeb on 10/12/16.
 */

function getUser(userData){
    if (userData.username === undefined) {
        $("#login").show();
    }
    else{
        $("#logout").show();
        $("#create-book").show();
    }
}
$.get("/user",getUser);

function getBooks(booksData) {
    var full_list = "";
    for (var i in booksData) {
        var book = booksData[i];
        full_list = full_list + 'Title: ' + book.title + '<br>' + 'Author:' + book.author +'<br>' + 'Genre: ' + book.genre + '<br>' + '<br>';
        var username = $("#username");
        var sender = "";
        book.sender = sender;
        if (sender === username){
            $("#update").show();
        }
    }
    $("#books").append(full_list);
}


$.get("/books", getBooks);