$(document).ready(function () {

    // define variables
    var bookId = $('#bookId').val();
    var ajaxUrl = "/api/v1/book/" + bookId + "/comments";
    $.ajax({
        type: "GET",
        url: ajaxUrl,
        success: function(response) {
            $("#comment-target").html( response );
        }
    });

});





