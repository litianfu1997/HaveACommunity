package com.tech4flag.community.service;

import com.tech4flag.community.mapper.AdminMapper;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-17 16:51
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 登陆功能
     * @param username
     * @param password
     * @return
     */
    public Boolean login(String username, String password) {
        //先判断用户名是否存在
        Admin admin = adminMapper.usernameIsExist(username);
        if (admin == null){
            return false;
        }else {
            //判断密码是否匹配
            Admin login = adminMapper.login(username,password);
            if (login != null){
                this.update();
                return true;
            }else {
                return false;
            }
        }

    }


    /**
     * 更新登录时间
     */
    public void update() {
        long currentTimeMillis = System.currentTimeMillis();
        adminMapper.update(currentTimeMillis);
    }

    /**
     * 审核文章
     * @param id
     */
    public void check(Integer id) {
        questionMapper.check(id);
    }
}
