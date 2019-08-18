package com.tech4flag.community.controller;

import com.tech4flag.community.dto.NotificationDTO;
import com.tech4flag.community.dto.PaginationDTO;
import com.tech4flag.community.dto.QuestionDTO;
import com.tech4flag.community.model.User;
import com.tech4flag.community.service.NotificationService;
import com.tech4flag.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-16 20:36
 */
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model, HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){
        User user = (User) request.getSession().getAttribute("user");
        if (user ==null){
            return "redirect:index";
        }
        String questions = "questions";
        String replies = "replies";
        if(action.equals(questions)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO<QuestionDTO> paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if (action.equals(replies)){
            PaginationDTO<NotificationDTO> paginationDTO = notificationService.list(user.getId(),page,size);
            Long unreadCount =notificationService.unreadCount(user.getId());
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("paginationN",paginationDTO);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        return "profile";
    }
}
