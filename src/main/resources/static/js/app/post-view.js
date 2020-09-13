$(document).ready(function () {
    // 서치 엔터키 이벤트 핸들러
    $('#post-search-text').on('keypress',function(e) {
        if(e.which == 13) {
            fn_v_post_search();
        }
    });

    // define variables
    var postId = $('#postId').val();
    var ajaxUrl = "/api/v1/post/" + postId + "/comments";

    $.ajax({
        type: "GET",
        url: ajaxUrl,
        success: function(response) {
            $("#comment-target").html( response );
        }
    });

    // Get the input field
    var input = document.getElementById("password");

    // Execute a function when the user releases a key on the keyboard
    input.addEventListener("keyup", function(event) {
      // Number 13 is the "Enter" key on the keyboard
      if (event.keyCode === 13) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Trigger the button element with a click
        document.getElementById("btn-password-check").click();
      }
    });

    // 다음 글 목록 조회
    var page = parseInt($("#page").val());
    var size = parseInt($("#size").val());
    var sort = $("#sort").val();

    // pagenation 설정
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                     // 5
    var pageBlockIndex = parseInt($("#page-block-index").val());                        // 100 /20

    var srhText = $("#post-search-text").val();
    var srhType = $("input[name='srhType']:checked").val();
    var ajaxUrl = "/posts?page="+(page-1)+"&size="+size+"&sort="+sort+"&pageBlockCnt="+pageBlockCnt+"&pageBlockIndex="+pageBlockIndex+"&srhText="+srhText+"&srhType="+srhType;

    $.ajax({
        type: "GET",
        url: ajaxUrl,
        success: function(response) {
            $("#post-list-target").html( response );
            if(String(response).indexOf("<input type='hidden' id='isLast' name='isLast' />") > 0){
                $("#get-more-list").show();
            }else{
                $("#get-more-list").hide();
            }
            fn_v_post_visited_post_check();             // 읽은 글 표시
        }
    });

    // pagenation 설정
    var postTotCnt = parseInt($("#post-tot-cnt").val());                        // 100 /20
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                    // 5
    var pageBlockIndex = parseInt($("#page-block-index").val());                  // pagination Index

    var totalPageCnt = Math.ceil(( postTotCnt / size ));

//    TODO: Pagination page 정확하게 그려 내기
    var curPageCnt =  totalPageCnt - (pageBlockIndex * pageBlockCnt)
    var paginationStr = "";

    if(pageBlockIndex >  0){
        paginationStr += "<li class='page-item' id='post-page-li-pre' onclick='javascript:fn_v_post_pagination_change_page_block("+(pageBlockIndex - 1)+",1)'><a class='page-link' >이전</a></li>";
    }
    if(curPageCnt <= 5 ){
        for(var i = (totalPageCnt - curPageCnt); i < totalPageCnt; i++){
            paginationStr += "<li class='page-item' id='post-page-li-"+(i+1)+"' onclick='javascript:fn_v_post_pagination_change_page("+(i+1)+")'><a class='page-link' >"+(i+1)+"</a></li>";
        }
    }else{
        for(var i = (totalPageCnt - curPageCnt); i < (totalPageCnt - curPageCnt) + 5; i++){
            paginationStr += "<li class='page-item' id='post-page-li-"+(i+1)+"' onclick='javascript:fn_v_post_pagination_change_page("+(i+1)+")'><a class='page-link' >"+(i+1)+"</a></li>";
        }
        var idx = parseInt($("#page-block-index").val()) + 1;
        paginationStr += "<li class='page-item' id='post-page-li-next' onclick='javascript:fn_v_post_pagination_change_page_block("+idx+",2)'><a class='page-link' >다음</a></li>";
    }

    $("#post-pagination-ul").html(paginationStr);
    $(".page-item").removeClass( "active" );

    $("#post-page-li-"+page ).addClass( "active" );
    fn_v_post_pagination_change_page(page);
});





