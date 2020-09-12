var comment = {
    init : function () {
        var _this = this;
        $('#btn-comment-save').on('click', function() {
            _this.save();
        });
    },
    save : function () {
        var data = {
            text: $('#comment').val(),
            password: $('#comment-password').val(),
            author: '채식인'
        };
        var postId = $('#postId').val();
        if(!data.text.trim()){
            alert("내용을 입력하세요.");
            return;
        } else if(!data.password.trim()){
            alert("비밀번호을 입력하세요.");
            return;
        } else if(data.password.trim().length < 2){
            alert("비밀번호를 최소 2자리 이상 입력하셔야 합니다. 쉬운 비밀번호는 타인이 수정 또는 삭제하기 쉬우니, 어려운 비밀번호를 입력해 주세요.");
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/post/'+ postId + '/comments',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('댓글이 등록되었습니다.');
            fm_v_get_comments(postId);
        }).fail(function (error) {
            alert('죄송합니다. 오류가 발생하였습니다!');
            //alert(JSON.stringify(error));
        });
    },
};

comment.init();
