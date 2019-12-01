package com.tech4flag.community.mapper;

import com.tech4flag.community.dto.QuestionFlag;
import com.tech4flag.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-24 23:55
 */
@Mapper
public interface QuestionLikeCountMapper {
    @Insert("insert into questionlikecount(user_id,question_id) values(#{userId},#{questionId})")
    void insert(QuestionFlag questionFlag);


    @Select("select count(1) from questionlikecount where question_id = #{id}")
    Integer selectLikeCount(Question question);

    @Select("select count(1) from questionlikecount where question_id = #{questionId} and user_id=#{userId}")
    Integer isLiked(QuestionFlag questionFlag);

    @Select("select * from questionlikecount where user_id=#{userId}")
    List<QuestionFlag> isLikedByUserId(@Param("userId") Integer userId);

    @Delete("delete from questionlikecount where question_id = #{id}")
    void removeById(@Param("id") Integer id);
}
