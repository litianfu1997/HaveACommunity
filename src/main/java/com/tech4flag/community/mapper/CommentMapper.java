package com.tech4flag.community.mapper;

import com.tech4flag.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-07 15:15
 */
@Mapper
public interface CommentMapper {
    @Insert("insert into comment(parent_id,type,commentator,gmt_modified,gmt_create,like_count,content)" +
            "value(#{parentId},#{type},#{commentator},#{gmtModified},#{gmtCreate},likeCount,content)")
    void insert(Comment comment);
}
