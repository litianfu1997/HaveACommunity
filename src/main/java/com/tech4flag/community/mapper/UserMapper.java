package com.tech4flag.community.mapper;

import com.tech4flag.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-09 23:26
 */
@Mapper
public interface UserMapper {


    /**
     * 用于插入新用户
     */
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) " +
            "values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    /***
     * 用于查询token是否存在与数据库内，若存在则证明登陆成功
     */
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer creator);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} " +
            "where id = #{id}")
    void update(User user);
}
