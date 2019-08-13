package com.tech4flag.community.mapper;

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
 * @date 2019-08-07 15:15
 */
@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id,type,commentator,gmt_modified,gmt_create,like_count,content) " +
            "values(#{parentId},#{type},#{commentator},#{gmtModified},#{gmtCreate},#{likeCount},#{content})")
    void insert(Comment comment);
    @Select("select * from comment where id = #{parentId}")
    Comment selectCommentByUserId(@Param("parentId") Long parentId);
    @Select("select * from comment where parent_id = #{parentId} and type=#{type}")
    List<Comment> selectCommentListByParentId(@Param("parentId") Long parentId,@Param("type") Integer type);
}
