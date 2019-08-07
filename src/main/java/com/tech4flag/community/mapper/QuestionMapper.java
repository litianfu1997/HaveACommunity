package com.tech4flag.community.mapper;

import com.tech4flag.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-13 17:38
 * create table question
 * (
 * id int auto_increment,
 * title varchar(50),
 * gmt_create bigint,
 * gmt_modified bigint,
 * creator int,
 * comment_count int default 0,
 * view_count int default 0,
 * like_count int default 0,
 * tag varchar(256),
 * constraint question_pk
 * primary key (id)
 * );
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag)" +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);
    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId")Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);
    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    Integer update(Question question);
    @Update("update question set view_count=view_count+1 where id=#{id}")
    Integer updateViewCount(Question question);

}
