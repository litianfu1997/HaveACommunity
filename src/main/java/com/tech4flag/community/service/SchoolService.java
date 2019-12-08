package com.tech4flag.community.service;

import com.tech4flag.community.mapper.SchoolMapper;
import com.tech4flag.community.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-08 22:51
 */
@Service
public class SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    public List<School> getAllSchool(){
        return schoolMapper.allSchool();
    }

    public School getSchoolById(Integer schoolId) {
        return schoolMapper.getSchoolById(schoolId);
    }
}
