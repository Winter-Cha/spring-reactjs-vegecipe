$(document).ready(function () {

    // 서치 엔터키 이벤트 핸들러
    $('#post-search-text').on('keypress',function(e) {
        if(e.which == 13) {
            fn_v_post_search();
        }
    });

    // define variables
    $("#page").val(1);
    $("#size").val(20);                     // 한번에 보여줄 글 수
    $("#sort").val("id,DESC");
    $("#page-block-cnt").val(5);            // pagination 에 표기될 페이지 수
    $("#page-block-index").val(0);          // pagination 다음/이전으로 이동하는 index 초기화

    var page = parseInt($("#page").val());
    var size = $("#size").val();
    var sort = $("#sort").val();
    // pagenation 설정
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                            // 5
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
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                     // 5

    var totalPageCnt = Math.ceil(( postTotCnt / size ));

    var paginationStr = "";
    if(totalPageCnt <= 5 ){
        for(var i = 0; i < totalPageCnt; i++){
            paginationStr += "<li class='page-item' id='post-page-li-"+(i+1)+"' onclick='javascript:fn_v_post_pagination_change_page("+(i+1)+")'><a class='page-link' >"+(i+1)+"</a></li>";
        }
    }else{
        for(var i = 0; i < 5; i++){
            paginationStr += "<li class='page-item' id='post-page-li-"+(i+1)+"' onclick='javascript:fn_v_post_pagination_change_page("+(i+1)+")'><a class='page-link' >"+(i+1)+"</a></li>";
        }
        var idx = parseInt($("#page-block-index").val()) + 1;
        paginationStr += "<li class='page-item' id='post-page-li-next' onclick='javascript:fn_v_post_pagination_change_page_block("+idx+",2)'><a class='page-link' >다음</a></li>";
    }
    $("#post-pagination-ul").html(paginationStr);
    $(".page-item").removeClass( "active" );
    $("#post-page-li-1").addClass( "active" );

});

