package com.tech4flag.community.mapper;

import com.tech4flag.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-07 15:15
 */
@Mapper
public interface CommentMapper {
    /**
     * 插入新回复
     * @param comment
     */
    @Insert("insert into comment(parent_id,type,commentator,gmt_modified,gmt_create,like_count,content) " +
            "values(#{parentId},#{type},#{commentator},#{gmtModified},#{gmtCreate},#{likeCount},#{content})")
    void insert(Comment comment);

    /**
     * 通过id查询评论
     * @param parentId
     * @return
     */
    @Select("select * from comment where id = #{parentId}")
    Comment selectCommentByUserId(@Param("parentId") Long parentId);

    /**
     * 通过父评论id查询子评论列表
     * @param parentId
     * @param type
     * @return
     */
    @Select("select * from comment where parent_id = #{parentId} and type=#{type}")
    List<Comment> selectCommentListByParentId(@Param("parentId") Long parentId,@Param("type") Integer type);

    /**
     * 回复评论数加1
     * @param comment
     */
    @Update("update comment set comment_count = comment_count+1 where id=#{id}")
    void incCommentCount(Comment comment);
}
