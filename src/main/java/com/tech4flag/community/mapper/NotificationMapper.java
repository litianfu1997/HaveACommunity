package com.tech4flag.community.mapper;

import com.tech4flag.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-17 13:20
 */
@Mapper
public interface NotificationMapper {
    @Insert("insert into notification(id,notifier,receiver,outerId,type,gmt_create,status,notifier_name,outer_title) " +
            "values(#{id},#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insertNotify(Notification notification);


    @Select("select count(1) from notification where receiver = #{userId} and status = 0")
    Integer unreadCountByUserId(@Param("userId") Integer userId);
    @Select("select count(1) from notification where receiver = #{userId} ")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from notification  where receiver = #{userId} limit #{offset},#{size}")
    List<Notification> selectNotificationList(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select * from notification  where id = #{id}")
    Notification selectById(@Param("id") Integer id);

    @Update("update notification set status = #{status} where id =#{id}")
    void updateStatus(Notification notification);
}
