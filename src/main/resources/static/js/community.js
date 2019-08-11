function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    $.ajax({
        type:"post",
        url:"/community/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            "type":1
        }),
        success:function(res){
            if (res.code == 200){
                $("#comment_section").hide();
            }else {
                if (res.code == 2003){
                    var b = confirm(res.message);
                    if (b){
                        window.open("https://github.com/login/oauth/authorize?client_id=979d5293cbf3c4a4c977&redirect_uri=http://localhost:8080/community/callback&scope=user&state=1")
                        window.localStorage.setItem("closable","true");
                    }
                } else {
                    alert(res.message);

                }
            }
            console.log(res);
        },
        dataType:"json"
    });
    console.log(questionId);
    console.log(commentContent);
}