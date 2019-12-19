package com.tech4flag.community.mapper;

import com.tech4flag.community.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-17 16:52
 */
@Mapper
public interface AdminMapper {

    @Select("select * from admin where username = #{v}")
    Admin usernameIsExist(@Param("v") String username);

    @Select("select * from admin where username=#{v1} and password=#{v2}")
    Admin login(@Param("v1") String username,@Param("v2") String password);

    @Update("update admin set admin.login_time = #{v}")
    void update(@Param("v") long currentTimeMillis);


}
