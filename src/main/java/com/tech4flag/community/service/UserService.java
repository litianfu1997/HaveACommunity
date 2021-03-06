package com.tech4flag.community.service;

import com.tech4flag.community.mapper.SchoolMapper;
import com.tech4flag.community.mapper.UserMapper;
import com.tech4flag.community.model.School;
import com.tech4flag.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-04 18:37
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null) {
            //插入操作
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新操作
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }

    public void Update(User user, String school) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());

        School dbSchool = schoolMapper.selectSchoolBySchoolName(school);
        //更新操作
        dbUser.setGmtModified(System.currentTimeMillis());
        dbUser.setAvatarUrl(user.getAvatarUrl());
        dbUser.setName(user.getName());
        dbUser.setBio(user.getBio());
        dbUser.setAge(user.getAge());
        dbUser.setSex(user.getSex());
        dbUser.setTag(user.getTag());
        dbUser.setSchoolId(dbSchool.getId());
        dbUser.setToken(user.getToken());
        userMapper.update(dbUser);

    }

    public User selectUserById(Integer id) {
        User user = userMapper.findById(id);
        return user;
    }
}
