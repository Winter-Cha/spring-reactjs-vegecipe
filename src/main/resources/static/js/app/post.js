var main = {
    passwordCheckType : "",
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });

        $('#btn-go-update').on('click', function() {
            _this.passwordCheckType = "update"
        });
        $('#btn-go-delete').on('click', function() {
            _this.passwordCheckType = "delete"
        });
        $('#btn-password-check').on('click', function() {
            _this.passwordCheck();
        });

        $('#btn-update').on('click', function() {
            _this.update();
        });
        $('#btn-delete').on('click', function() {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            password: $('#password').val(),
            author: $('#author').val(),
            category: $("input[name='category']:checked").val(),
            content: $('#content').val(),
            authorEmail: $('#authorEmail').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/post',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/community';
        }).fail(function (error) {
            alert('죄송합니다. 오류가 발생하였습니다!');
            //alert(JSON.stringify(error));
        });
    },
    passwordCheck : function() {
       var data = {
           id : $('#id').val(),
           password: $('#password').val(),
           passwordCheckType : this.passwordCheckType
       };
       $.ajax({
           type: 'POST',
           url: '/api/v1/post/check/password',
           dataType: 'json',
           contentType: 'application/json; charset=utf-8',
           data: JSON.stringify(data)
       }).done(function() {
           //alert('글이 수정되었습니다.');
           //window.location.href = '/community';
       }).fail(function (error) {
           if(error.status == 200){
               if ( data.passwordCheckType == "update") {
                    var ajaxUrl = "/api/v1/post/" + data.id + "/update";
                    $.ajax({
                       type: "POST",
                       url: ajaxUrl,
                       dataType: 'json',
                       contentType: 'application/json; charset=utf-8',
                       data: JSON.stringify(data)
                   }).done(function(data){
                   }).fail(function (error) {
                       if(error.status == 200){
                           $("#post-content").html( error.responseText );
                           $('#modal-password').modal('hide');
                           $('body').removeClass('modal-open');
                           //modal-open class is added on body so it has to be removed

                           $('.modal-backdrop').remove();
                           //need to remove div with modal-backdrop class
                       } else {
                           alert('죄송합니다. 오류가 발생하였습니다!');
                       }

                   });
               } else if ( data.passwordCheckType == "delete") {
                    if(confirm("글 삭제 시 복구가 안됩니다. 삭제하시겠습니까?")){
                        $.ajax({
                            type: 'DELETE',
                            url: '/api/v1/post/'+data.id,
                            dataType: 'json',
                            contentType: 'application/json; charset=utf-8',
                            data: JSON.stringify(data)
                        }).done(function() {
                            alert('글이 삭제되었습니다.');
                            window.location.href = '/community';
                        }).fail(function (error) {
                            alert('죄송합니다. 오류가 발생하였습니다!');
                            //alert(JSON.stringify(error));
                        });
                    }
               }
           } else if(error.status == 999){
               alert('비밀번호가 틀립니다.');
           } else {
               alert('죄송합니다. 오류가 발생하였습니다!');
               //alert(JSON.stringify(error));
           }
       });
   },
    update : function() {
        var data = {
            id: $('#id').val(),
            title: $('#title').val(),
            content: $('#content').val(),
            password: $('#pwd').val()
        };
        var id = $('#id').val();
        $.ajax({
            type: 'PUT',
            url: '/api/v1/post/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/community';
        }).fail(function (error) {
            alert('죄송합니다. 오류가 발생하였습니다!');
            //alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        if(confirm("삭제하시겠습니까?")){
            $.ajax({
                type: 'DELETE',
                url: '/api/v1/post/'+id,
                dataType: 'json',
                contentType: 'application/json; charset=utf-8'
            }).done(function() {
                alert('글이 삭제되었습니다.');
                window.location.href = '/community';
            }).fail(function (error) {
                alert('죄송합니다. 오류가 발생하였습니다!');
                //alert(JSON.stringify(error));
            });
        }
    },
    // 웹에디터 이미지 파일 체크
    checkImageTag : function(){
        alert("죄송합니다. 이미지는 입력하실 수 없습니다.");
        var contents = $('.post-content').summernote('code');
        contents = contents.replace(/<img[^>]*>/g,"");
        $('.post-content').summernote("code", contents);
    }
};

main.init();

