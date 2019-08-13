/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId,1,commentContent);

}
function comment2target(targetId,type,content) {
    if (!content){
        alert("回复内容不能为空！")
        return
    }
    $.ajax({
        type:"post",
        url:"/community/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success:function(res){
            if (res.code == 200){
                window.location.reload();
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
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}
/**
 * 展开二级评论
 */
var flag = true;
function collapseComments(e) {
    var id  = e.getAttribute("data-id");
    if (flag){//展开评论
        flag = !flag;
        $.getJSON("/community/comment/"+id,function(data){
           console.log(data);
           var subCommentContainer = $("#comment-"+id);
            $.each(data.data,function (index,comment) {
                var c =$("<div/>",{
                    "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    html:comment.content
                });
                subCommentContainer.prepend(c);
            });
            $("#comment-"+id).addClass("in");
            e.classList.add("active");
        });

    } else {//折叠评论
        flag = !flag;
        $("#comment-"+id).removeClass("in");
        e.classList.remove("active");
    }

}