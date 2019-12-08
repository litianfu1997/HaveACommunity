package com.tech4flag.community.mapper;

import com.tech4flag.community.model.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-08 22:45
 */
@Mapper
public interface SchoolMapper {

    /**
     * 通过学校名查找学校
     * @param schoolName
     * @return
     */
    @Select("select * from school where name = #{schoolName}")
    School selectSchoolBySchoolName(@Param("schoolName") String schoolName);

    /**
     * 查询所有学校
     * @return
     */
    @Select("select * from school")
    List<School> allSchool();

    /**
     * 通过学校id查找学校
     * @param schoolId
     * @return
     */
    @Select("select * from school where id = #{schoolId}")
    School getSchoolById(@Param("schoolId") Integer schoolId);
}
