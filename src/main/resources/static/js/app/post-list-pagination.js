// 페이지 선택 후 이벤트 헨들러
function fn_v_post_pagination_change_page(pageNum){
    //alert(pageNum);
    // page block index
    $("#page").val(pageNum);

    var page = parseInt($("#page").val());
    var size = parseInt($("#size").val());
    var sort = $("#sort").val();

    $(".page-item").removeClass( "active" );
    $("#post-page-li-"+pageNum).addClass( "active" );

    var ajaxUrl = "/posts?page="+(page-1)+"&size="+size+"&sort="+sort;

    $.ajax({
        type: "GET",
        url: ajaxUrl,
        success: function(response) {
            var preList = String($("#post-list-target").html());
            $("#post-list-target").html( response );

            if(String(response).indexOf("<input type='hidden' id='isLast' name='isLast' />") > 0){
                $("#get-more-list").show();
            }else{
                $("#get-more-list").hide();
            }
        }
    });
};

// 더보기 버튼 클릭 후 이벤트 헨들러
function fn_v_post_get_more_list(pageNum){
    //alert(pageNum);
    // page block index
    $("#page").val(pageNum);

    var page = parseInt($("#page").val());
    var size = parseInt($("#size").val());
    var sort = $("#sort").val();

    var idx = parseInt($("#page-block-index").val()) + 1;
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                     // 5
    //alert( idx * pageBlockCnt  );

    if(pageNum > (idx * pageBlockCnt) ){
        fn_v_post_pagination_change_page_block(idx , null ,true);
    }

    $(".page-item").removeClass( "active" );
    $("#post-page-li-"+pageNum).addClass( "active" );


    var ajaxUrl = "/posts?page="+(page-1)+"&size="+size+"&sort="+sort;

    $.ajax({
        type: "GET",
        url: ajaxUrl,
        success: function(response) {
            var preList = String($("#post-list-target").html());
            preList.replace("<input type='hidden' id='isLast' name='isLast' />", "");
            $("#post-list-target").html( preList + response );

            if(String(response).indexOf("<input type='hidden' id='isLast' name='isLast' />") > 0){
                $("#get-more-list").show();
            }else{
                $("#get-more-list").hide();
            }
        }
    });
};

// 페이지 선택 후 이벤트 헨들러
function fn_v_post_pagination_change_page_block(pageBlockIndex, mode, moreFlag){
    //alert(pageBlockIndex);
    $("#page-block-index").val(pageBlockIndex);
    var page = $("#page").val();
    var size = $("#size").val();
    var sort = $("#sort").val();

    // pagenation 설정
    var postTotCnt = parseInt($("#post-tot-cnt").val());                      // 총 글수
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                // pagination 블럭의 수 5

    var totalPageCnt = Math.ceil(( postTotCnt / size ));

    //console.log(totalPageCnt);

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

    if(moreFlag) {
        return;
    }

    if(mode == 1) {         // 이전
        var pageIdx = (totalPageCnt - curPageCnt) + 5;
        $("#post-page-li-"+pageIdx ).addClass( "active" );
        fn_v_post_pagination_change_page(pageIdx);
    } else {                // 다음
        var pageIdx = (totalPageCnt - curPageCnt) + 1;
        $("#post-page-li-"+pageIdx ).addClass( "active" );
        fn_v_post_pagination_change_page(pageIdx);
    }

};

// 더보기 버튼 이벤트 핸들러
function fn_v_getMoreList() {
    var page = parseInt($("#page").val());
    fn_v_post_get_more_list(page+1);
};

// 게시글  view page 링크
function fn_v_post_view_post(postId){
    var page = $("#page").val();
    var size = $("#size").val();
    var sort = $("#sort").val();
    // pagenation 설정
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                     // 5
    var pageBlockIndex = parseInt($("#page-block-index").val());                        // 100 /20
    window.location.href = "/post/view/"+parseInt(postId)+"/page?page="+page+"&size="+size+"&sort="+sort+"&pageBlockCnt="+pageBlockCnt+"&pageBlockIndex="+pageBlockIndex;

}

