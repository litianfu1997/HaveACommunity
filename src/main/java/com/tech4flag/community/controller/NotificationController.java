package com.tech4flag.community.controller;

import com.tech4flag.community.dto.NotificationDTO;
import com.tech4flag.community.dto.PaginationDTO;
import com.tech4flag.community.dto.QuestionDTO;
import com.tech4flag.community.enums.NotificationTypeEnum;
import com.tech4flag.community.model.User;
import com.tech4flag.community.service.NotificationService;
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
 * @date 2019-08-18 15:35
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id, Model model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user ==null){
            return "redirect:index";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType().equals(notificationDTO.getType())
                || NotificationTypeEnum.REPLY_QUESTION.getType().equals(notificationDTO.getType())){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:index";
        }


    }
}
