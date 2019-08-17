package com.tech4flag.community.mapper;

import com.tech4flag.community.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-17 13:20
 */
@Mapper
public interface NotificationMapper {
    @Insert("insert into notification(id,notifier,receiver,outerId,type,gmt_create,status) " +
            "value(#{id},#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status})")
    void insertNotify(Notification notification);
}
