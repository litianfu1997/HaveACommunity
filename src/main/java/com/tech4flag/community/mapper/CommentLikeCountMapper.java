package com.tech4flag.community.mapper;

import com.tech4flag.community.dto.CommentFlag;

import com.tech4flag.community.model.Comment;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-24 23:55
 */
@Mapper
public interface CommentLikeCountMapper {
    @Insert("insert into commentlikecount(user_id,comment_id) values(#{userId},#{commentId})")
    void insert(CommentFlag commentFlag);

    @Select("select count(1) from commentlikecount where comment_id = #{id}")
    Integer selectLikeCount(Comment comment);

    @Select("select count(1) from commentlikecount where comment_id = #{commentId} and user_id=#{userId}")
    Integer isLiked(CommentFlag commentFlag);

    @Select("select * from commentlikecount where user_id=#{userId}")
    List<CommentFlag> isLikedByUserId(@Param("userId") Integer userId);
}
