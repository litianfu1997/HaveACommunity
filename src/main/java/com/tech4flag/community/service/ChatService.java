package com.tech4flag.community.service;

import com.tech4flag.community.enums.NotificationStatusEnum;
import com.tech4flag.community.mapper.ChatMapper;
import com.tech4flag.community.mapper.NotificationMapper;
import com.tech4flag.community.mapper.UserMapper;
import com.tech4flag.community.model.Chat;
import com.tech4flag.community.dto.ChatDto;
import com.tech4flag.community.model.Notification;
import com.tech4flag.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-20 16:53
 */
@Service
public class ChatService {

    @Autowired
    private NotificationMapper notificationMapper;


    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private UserMapper userMapper;


    public void insertChat(Chat chat){
        chatMapper.insertChat(chat);
    }

    public void createNotify(Integer fromUser,Integer toUser,Integer type){

        //私聊通知
        Notification notification =new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);

        //接收到通知的人
        notification.setNotifier(fromUser);
        //设置未读状态
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(toUser);
        User u = userMapper.findById(fromUser);
        notification.setNotifierName(u.getName());
        notificationMapper.insertNotify(notification);
    }

    /**
     * 查询两人的聊天记录
     * @param fromUser
     * @param toUser
     * @return
     */
    public List<ChatDto> selectChatListByFromUserIdAndToUser(Integer fromUser, Integer toUser) {

        List<ChatDto> chatDtos = new ArrayList<>();

        List<Chat> chats = chatMapper.selectChatListByFromUserIdAndToUser(fromUser,toUser);
        for (Chat chat : chats) {
            ChatDto chatDto = new ChatDto();
            BeanUtils.copyProperties(chat,chatDto);
            User fUser = userMapper.findById(chat.getFromUserId());
            User tUser = userMapper.findById(chat.getToUserId());
            chatDto.setFromUserName(fUser.getName());
            chatDto.setToUserName(tUser.getName());
            chatDtos.add(chatDto);
        }

        return chatDtos;
    }
}
