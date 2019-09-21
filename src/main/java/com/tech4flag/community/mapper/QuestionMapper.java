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
    /**
     * 插入问题
     * @param question
     */
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag)" +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    /**
     * 查询所有问题
     * @return
     */
    @Select("select * from question ORDER BY gmt_create DESC")
    List<Question> allList();

    /**
     * 查询问题列表
     * @param offset
     * @param size
     * @return
     */
    @Select("select * from question ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId")Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    Integer update(Question question);

    @Update("update question set like_count=like_count+1 where id=#{id}")
    Integer updateLikeCount(Question question);

    @Update("update question set view_count=view_count+1 where id=#{id}")
    Integer updateViewCount(Question question);

    /**
     * 相关问题的查询
     * @param id
     * @param tagRegexp
     * @return
     */
    @Select("select id,title,tag from question where tag regexp #{tagRegexp} and id != #{id}")
    List<Question> relevantQuestion(@Param("id") Integer id,@Param("tagRegexp") String tagRegexp);

    /**
     * 评论数加1
     * @param question
     * @return
     */
    @Update("update question set comment_count=comment_count+1 where id=#{id}")
    Integer updateCommentCount(Question question);

    /**
     * 通过id查询问题
     * @param parentId
     * @return
     */
    @Select("select * from question where id=#{parentId}")
    Question selectQuestionById(@Param("parentId") Long parentId);

    @Select("select count(1) from question where title like #{search} or tag like #{search}")
    Integer countByTags(@Param("search")String search);

    @Select("select count(1) from question where tag like #{tag}")
    Integer countByHotTag(@Param("tag")String tag);

    @Select("select * from question where title like #{search}  ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Question> listByTags(@Param("search") String search, @Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question where tag like #{tag}   ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Question> listByHotTag(@Param("tag") String tag, @Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question ORDER BY comment_count DESC limit #{offset},#{size}")
    List<Question> listByHot( @Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question where comment_count=0  ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Question> listByNoReply( @Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select * from question ORDER BY view_count DESC limit #{offset},#{size}")
    List<Question> listByViewCount( @Param("offset") Integer offset,@Param("size") Integer size);
}
