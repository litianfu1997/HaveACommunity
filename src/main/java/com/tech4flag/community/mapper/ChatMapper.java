package com.tech4flag.community.mapper;

import com.tech4flag.community.model.Chat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-20 16:54
 */
@Mapper
public interface ChatMapper {

    @Insert("insert into chat(from_user_id,to_user_id,msg,time,flag)" +
            " values(#{fromUserId},#{toUserId},#{msg},#{time},#{flag})")
    void insertChat(Chat chat);

    @Select("select * from chat where (from_user_id=#{fromUser} and to_user_id=#{toUser}) || (from_user_id=#{toUser} and to_user_id=#{fromUser})")
    List<Chat> selectChatListByFromUserIdAndToUser(@Param("fromUser") Integer fromUser,@Param("toUser") Integer toUser);
}
