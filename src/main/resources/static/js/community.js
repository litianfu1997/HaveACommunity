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
            console.log(res);
        },
        dataType:"json"
    });
    console.log(questionId);
    console.log(commentContent);
}