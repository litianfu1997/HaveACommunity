package com.tech4flag.community.service;

import com.tech4flag.community.dto.NotificationDTO;
import com.tech4flag.community.dto.PaginationDTO;
import com.tech4flag.community.enums.NotificationStatusEnum;
import com.tech4flag.community.enums.NotificationTypeEnum;
import com.tech4flag.community.exception.CustomizeErrorCode;
import com.tech4flag.community.exception.CustomizeException;
import com.tech4flag.community.mapper.NotificationMapper;
import com.tech4flag.community.mapper.UserMapper;
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
 * @date 2019-08-17 13:43
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public  PaginationDTO<NotificationDTO> list(Integer userId, Integer page, Integer size) {
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        Integer totalCount = notificationMapper.countByUserId(userId);
        if (totalCount%size==0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size+1;
        }

        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);

        List<Notification> notifications = notificationMapper.selectNotificationList(userId,offset,size);
        if (notifications.size() == 0){
            return paginationDTO;
        }

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO =new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTO.setOuterId(notification.getOuterId());
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Integer id) {
        Integer count = notificationMapper.unreadCountByUserId(id);
        return Long.valueOf(count);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if (notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!(notification.getReceiver().equals(user.getId()))){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //更新已读信息
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateStatus(notification);

        NotificationDTO notificationDTO =new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));

        return notificationDTO;
    }
}
