package com.tech4flag.community.controller;

import com.tech4flag.community.dto.CommentCreateDTO;
import com.tech4flag.community.dto.ResultDTO;
import com.tech4flag.community.exception.CustomizeErrorCode;
import com.tech4flag.community.model.Comment;
import com.tech4flag.community.model.User;
import com.tech4flag.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-07 15:00
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/comment")
    public @ResponseBody
    Object comment(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user ==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment =new Comment();
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(1);
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);

        return ResultDTO.errorOf(200,"评论成功");
    }
}
