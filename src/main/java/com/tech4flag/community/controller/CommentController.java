package com.tech4flag.community.controller;

import com.tech4flag.community.dto.CommentDTO;
import com.tech4flag.community.dto.ResultDTO;
import com.tech4flag.community.exception.CustomizeErrorCode;
import com.tech4flag.community.mapper.CommentMapper;
import com.tech4flag.community.model.Comment;
import com.tech4flag.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-07 15:00
 */
@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    @RequestMapping("/comment")
    public @ResponseBody
    Object comment(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user ==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment =new Comment();
        comment.setType(commentDTO.getType());
        comment.setCommentator(1);
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1);
        comment.setLikeCount(0L);
//        commentMapper.insert(comment);

        return ResultDTO.errorOf(200,"评论成功");
    }
}
