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
        var bookId = $('#bookId').val();
        if(!data.text.trim()){
            alert("내용을 입력하세요.");
            return;
//        } else if (fn_oveerMaxInputLimit(data.text.trim())) {
//            alert("최대 글자수를 초과하였습니다.");
//            $("#comment").summernote("code",fn_getTextInputLimit(data.text.trim()));
//            return;
        } else if(!data.password.trim()){
            alert("비밀번호을 입력하세요.");
            return;
        } else if(data.password.trim().length < 2){
            alert("비밀번호를 최소 2자리에서 10자리까지 입력하셔야 합니다. 쉬운 비밀번호는 타인이 수정 또는 삭제하기 쉬우니, 어려운 비밀번호를 입력해 주세요.");
            return;
        }

        $.ajax({
            type: 'POST',
            url: '/api/v1/book/'+ bookId + '/comments',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('댓글이 등록되었습니다.');
            fm_v_get_comments(bookId);
        }).fail(function (error) {
            alert('죄송합니다. 오류가 발생하였습니다!. 글자수를 줄여보세요.');
            //alert(JSON.stringify(error));
        });
    },
};

comment.init();

// TEXTAREA 최대값 체크
    function fn_oveerMaxInputLimit(t) {
        var tempText = t;
        var tempChar = "";                                        // TextArea의 문자를 한글자씩 담는다
        var tempChar2 = "";                                        // 절삭된 문자들을 담기 위한 변수
        var countChar = 0;                                        // 한글자씩 담긴 문자를 카운트 한다
        var tempHangul = 0;                                        // 한글을 카운트 한다
        var maxSize = 400;                                        // 최대값

        // 글자수 바이트 체크를 위한 반복
        for(var i = 0 ; i < tempText.length; i++) {
            tempChar = tempText.charAt(i);

            // 한글일 경우 2 추가, 영문일 경우 1 추가
            if(escape(tempChar).length > 4) {
                //countChar += 2;
                countChar++;
                tempHangul++;
            } else {
                countChar++;
            }
        }

        // 카운트된 문자수가 MAX 값을 초과하게 되면 절삭 수치까지만 출력을 한다.(한글 입력 체크)
        // 내용에 한글이 입력되어 있는 경우 한글에 해당하는 카운트 만큼을 전체 카운트에서 뺀 숫자가 maxSize보다 크면 수행
        if((countChar) > maxSize) {
            return true;
        }
        return false;
    }


    // TEXTAREA 최대값 까지 자르기
        function fn_getTextInputLimit(t) {
            var tempText = t;
            var tempChar = "";                                        // TextArea의 문자를 한글자씩 담는다
            var tempChar2 = "";                                        // 절삭된 문자들을 담기 위한 변수
            var countChar = 0;                                        // 한글자씩 담긴 문자를 카운트 한다
            var tempHangul = 0;                                        // 한글을 카운트 한다
            var maxSize = 400;                                        // 최대값

            // 글자수 바이트 체크를 위한 반복
            for(var i = 0 ; i < tempText.length; i++) {
                tempChar = tempText.charAt(i);

                // 한글일 경우 2 추가, 영문일 경우 1 추가
                if(escape(tempChar).length > 4) {
                    //countChar += 2;
                    countChar++;
                    tempHangul++;
                } else {
                    countChar++;
                }

                if(countChar > maxSize) {
                    alert(tempText.substr(0, i -1));
                    return tempText.substr(0, i -1);
                }
            }

            return tempText;
        }
