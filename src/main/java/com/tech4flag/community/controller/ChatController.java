package com.tech4flag.community.controller;

import com.tech4flag.community.dto.ChatDto;
import com.tech4flag.community.model.Chat;
import com.tech4flag.community.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-20 09:52
 */
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping("/chat")
    public String chat( Model model){
        return "websocketTest";
    }

    @RequestMapping("/chatHistory")
    @ResponseBody
    public List<ChatDto> chatHistory(@RequestParam("fromUser")Integer fromUser
                                    , @RequestParam("toUser")Integer toUser){
        List<ChatDto> chats = new ArrayList<>();
        if (fromUser != null && toUser != null){
            chats = chatService.selectChatListByFromUserIdAndToUser(fromUser,toUser);
        }else {
            System.out.println("id不能为空");
            return null;
        }
        return chats;
    }
}
