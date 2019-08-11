package com.tech4flag.community.controller;

import com.tech4flag.community.dto.CommentDTO;
import com.tech4flag.community.dto.QuestionDTO;
import com.tech4flag.community.service.CommentService;
import com.tech4flag.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-21 14:01
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model){
        //累加阅读数
        questionService.incView(id);
        List<CommentDTO> comments = commentService.selectCommentListByParentId(id);
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
