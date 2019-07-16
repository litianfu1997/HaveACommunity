package com.tech4flag.community.controller;

import com.tech4flag.community.dto.PaginationDTO;
import com.tech4flag.community.dto.QuestionDTO;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.mapper.UserMapper;
import com.tech4flag.community.model.Question;
import com.tech4flag.community.model.User;
import com.tech4flag.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-08 22:53
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        if (cookies !=null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
