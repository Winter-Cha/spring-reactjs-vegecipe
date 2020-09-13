// 페이지 선택 후 이벤트 헨들러
function fn_v_post_pagination_change_page(pageNum){
    //alert(pageNum);
    // page block index
    $("#page").val(pageNum);

    var page = parseInt($("#page").val());
    var size = parseInt($("#size").val());
    var sort = $("#sort").val();
    // pagenation 설정
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                            // 5
    var pageBlockIndex = parseInt($("#page-block-index").val());                        // 100 /20

    $(".page-item").removeClass( "active" );
    $("#post-page-li-"+pageNum).addClass( "active" );


    var srhText = $("#post-search-text").val();
    var srhType = $("input[name='srhType']:checked").val();

    var ajaxUrl = "/posts?page="+(page-1)+"&size="+size+"&sort="+sort+"&pageBlockCnt="+pageBlockCnt+"&pageBlockIndex="+pageBlockIndex+"&srhText="+srhText+"&srhType="+srhType;

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

            fn_v_post_visited_post_check();             // 읽은 글 표시
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
    // pagenation 설정
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                     // 5
    var pageBlockIndex = parseInt($("#page-block-index").val());                        // 100 /20
    //alert( idx * pageBlockCnt  );

    if(pageNum > (idx * pageBlockCnt) ){
        fn_v_post_pagination_change_page_block(idx , null ,true);
    }

    $(".page-item").removeClass( "active" );
    $("#post-page-li-"+pageNum).addClass( "active" );

    var srhText = $("#post-search-text").val();
    var srhType = $("input[name='srhType']:checked").val();
    var ajaxUrl = "/posts?page="+(page-1)+"&size="+size+"&sort="+sort+"&pageBlockCnt="+pageBlockCnt+"&pageBlockIndex="+pageBlockIndex+"&srhText="+srhText+"&srhType="+srhType;

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

            fn_v_post_visited_post_check();             // 읽은 글 표시
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
    $(this).addClass('active');
    var page = $("#page").val();
    var size = $("#size").val();
    var sort = $("#sort").val();
    // pagenation 설정
    var pageBlockCnt = parseInt($("#page-block-cnt").val());                            // 5
    var pageBlockIndex = parseInt($("#page-block-index").val());                        // 100 /20

    var visitedPosts = JSON.parse(localStorage.getItem('visitedPosts')) || [];

    var id = postId;
    var index = visitedPosts.indexOf(id);

    //if (!id) return;

    if (index == -1) {
        visitedPosts.push(id);
        $("#post_id_"+postId).addClass('active');              // 일반 화면
        $("#post_id_xs_"+postId).addClass('active');           // 모바일 화면

    }

    localStorage.setItem('visitedPosts', JSON.stringify(visitedPosts));

    var srhText = $("#post-search-text").val();
    var srhType = $("input[name='srhType']:checked").val();
    window.location.href = "/post/view/"+parseInt(postId)+"/page?page="+page+"&size="+size+"&sort="+sort+"&pageBlockCnt="+pageBlockCnt+"&pageBlockIndex="+pageBlockIndex+"&srhText="+srhText+"&srhType="+srhType;

}


// 읽은 글 표시
function fn_v_post_visited_post_check(){
    var visitedPosts = JSON.parse(localStorage.getItem('visitedPosts')) || [];
    visitedPosts.forEach(function(visitedPost) {
        $("#post_id_"+visitedPost).addClass('active');              // 일반 화면
        $("#post_id_xs_"+visitedPost).addClass('active');           // 모바일 화면
    });
}

// 포스트 검색
function fn_v_post_search(){
    var srhText = $("#post-search-text").val();
    var srhType = $("input[name='srhType']:checked").val();
    window.location = '/community?srhText=' + srhText+"&srhType="+srhType;
}
