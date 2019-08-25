package com.tech4flag.community.controller;

import com.tech4flag.community.dto.CommentFlag;
import com.tech4flag.community.dto.QuestionFlag;
import com.tech4flag.community.mapper.CommentLikeCountMapper;
import com.tech4flag.community.mapper.CommentMapper;
import com.tech4flag.community.mapper.QuestionLikeCountMapper;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.model.Comment;
import com.tech4flag.community.model.Question;
import com.tech4flag.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-24 18:45
 */
@Controller
public class LikeCountController {
    @Autowired
    private QuestionLikeCountMapper questionLikeCountMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentLikeCountMapper commentLikeCountMapper;
    @Transactional
    @RequestMapping ("/questionLike")
    public @ResponseBody QuestionFlag questionLike(@RequestBody Map<String,Object> params  , Model model,
                                            HttpServletRequest request){
        String id =  params.get("questionId").toString();
        System.out.println(id);
        if(id == null){
            System.out.println("id为空");
            return null;
        }
        User user = (User)request.getSession().getAttribute("user");

        //通过id查询问题
        Question question = questionMapper.getById(Integer.valueOf(id));


        QuestionFlag questionFlag = new QuestionFlag();
        questionFlag.setUserId(user.getId());
        questionFlag.setQuestionId(question.getId());
        Integer liked = questionLikeCountMapper.isLiked(questionFlag);


        //如果该用户没有点过这批文章的赞
        if (liked==0){
            //插入点赞
            questionLikeCountMapper.insert(questionFlag);
            //查询该文章的点赞数
            Integer likeCount = questionLikeCountMapper.selectLikeCount(question);
            //设置该文章的点赞数
            question.setLikeCount(likeCount);
            //文章更新
            questionMapper.updateLikeCount(question);
            questionFlag.setFlag(liked+1);
        }else {
            System.out.println("已经点过赞了");
            return questionFlag;
        }

        return questionFlag;
    }

    @RequestMapping("/likedList")
    public @ResponseBody List<QuestionFlag> likedList(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        List<QuestionFlag> likedList = questionLikeCountMapper.isLikedByUserId(user.getId());
        return likedList;
    }
    @RequestMapping("/commentLikedList")
    public @ResponseBody List<CommentFlag> commentLikedList(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        List<CommentFlag> likedList = commentLikeCountMapper.isLikedByUserId(user.getId());
        return likedList;
    }

    @Transactional
    @RequestMapping ("/commentLike")
    public @ResponseBody
    CommentFlag commentLike(@RequestBody Map<String,Object> params  , Model model,
                             HttpServletRequest request){
        String id =  params.get("commentId").toString();
        System.out.println(id);
        if(id == null){
            System.out.println("id为空");
            return null;
        }
        User user = (User)request.getSession().getAttribute("user");

        //通过id查询问题
        Comment comment = commentMapper.getById(Integer.valueOf(id));


        CommentFlag commentFlag =new CommentFlag();
        commentFlag.setUserId(user.getId());
        commentFlag.setCommentId(comment.getId());
        Integer liked = commentLikeCountMapper.isLiked(commentFlag);


        //如果该用户没有点过这批文章的赞
        if (liked==0){
            //插入点赞
            commentLikeCountMapper.insert(commentFlag);
            //查询该文章的点赞数
            Integer likeCount = commentLikeCountMapper.selectLikeCount(comment);
            //设置该文章的点赞数
            comment.setLikeCount(Long.valueOf(likeCount));
            //文章更新
            commentMapper.updateLikeCount(comment);
            commentFlag.setFlag(liked+1);
        }else {
            System.out.println("已经点过赞了");
            return commentFlag;
        }

        return commentFlag;
    }

}
