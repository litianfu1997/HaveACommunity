package com.tech4flag.community.controller;

import com.tech4flag.community.cache.TagCache;
import com.tech4flag.community.model.School;
import com.tech4flag.community.model.User;
import com.tech4flag.community.service.SchoolService;
import com.tech4flag.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-08 20:30
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolService schoolService;

    @RequestMapping("/user")
    public String user(Model model, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<School> schoolList = schoolService.getAllSchool();
        School school = schoolService.getSchoolById(user.getSchoolId());
        model.addAttribute("schoolList",schoolList);
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("user",user);
        model.addAttribute("schoolName",school.getName());
        return "user";
    }

    @RequestMapping("/updateUser")
    public String updateUser(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("age") Integer age,
                             @RequestParam("bio") String bio, @RequestParam("tag") String tag,
                             @RequestParam("school") String school, @RequestParam("sex") String sex,
                             Model model,HttpServletRequest request) {
        User sessionUser = (User)request.getSession().getAttribute("user");
        model.addAttribute("user",sessionUser);
        List<School> schoolList = schoolService.getAllSchool();
        model.addAttribute("schoolList",schoolList);
        User user = userService.selectUserById(id);
        user.setSex(sex);
        user.setName(name);
        user.setTag(tag);
        user.setAge(age);
        user.setBio(bio);
        System.out.println(school);
        userService.Update(user,school);
        School userSchool = schoolService.getSchoolById(user.getSchoolId());
        model.addAttribute("schoolName",userSchool.getName());

        return "redirect:user";
    }


}
