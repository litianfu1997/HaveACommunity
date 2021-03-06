package com.tech4flag.community.controller;

import com.tech4flag.community.dto.AdminQuestionDTO;
import com.tech4flag.community.model.Message;
import com.tech4flag.community.model.Question;
import com.tech4flag.community.service.AdminQuestionService;
import com.tech4flag.community.service.AdminService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-10-04 10:48
 */
@Controller
public class AdminController {
    @Autowired
    private AdminQuestionService adminQuestionService;

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin")
    public String AdminIndex(){
        return "admin";
    }

    @RequestMapping("/adminLogout")
    public String adminLogout(){
        return "redirect:admin";
    }

    @RequestMapping("/adminLogin")
    public String adminLogin(@RequestParam("username")String username,
                             @RequestParam("password")String password,
                            Model model){
        Boolean b = adminService.login(username,password);
        if (!b.equals(true)){
            model.addAttribute("msg","用户不存在或者密码错误");
            return "admin";
        }else {
            adminService.update();
            model.addAttribute("msg","登录成功");
            model.addAttribute("username",username);
            return "adminView";
        }
    }


    /**
     * 已审核的文章列表
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping("/questionListAdmin")
    @ResponseBody
    public Map<String,Object> questionList(@Param("limit")Integer limit,@Param("page")Integer page){
        page = (page-1)*limit;
        List<AdminQuestionDTO> list = adminQuestionService.list(page, limit);
        Integer count = adminQuestionService.count();
        Map<String,Object> map = new HashMap<String,Object>();
        //code=0代表请求成功?
        map.put("code",0);
        //返回的提示信息
        map.put("msg","");
        //count代表数据的总条数,前端会根据他计算出页码数
        map.put("count",count);
        //data代表我们的数据格式为数组
        map.put("data",list);
        return map;
    }

    /**
     * 未审核的文章列表
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping("/uncheckList")
    @ResponseBody
    public Map<String,Object> uncheckList(@Param("limit")Integer limit,@Param("page")Integer page){
        page = (page-1)*limit;
        List<AdminQuestionDTO> list = adminQuestionService.uncheckList(page, limit);
        Integer count = adminQuestionService.unCount();
        Map<String,Object> map = new HashMap<String,Object>();
        //code=0代表请求成功?
        map.put("code",0);
        //返回的提示信息
        map.put("msg","");
        //count代表数据的总条数,前端会根据他计算出页码数
        map.put("count",count);
        //data代表我们的数据格式为数组
        map.put("data",list);
        return map;
    }

    /**
     * 所有文章列表
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping("/allQuestionList")
    @ResponseBody
    public Map<String,Object> allQuestionList(@Param("limit")Integer limit,@Param("page")Integer page){
        page = (page-1)*limit;
        List<AdminQuestionDTO> list = adminQuestionService.allQuestionList(page, limit);
        Integer count = adminQuestionService.allCount();
        Map<String,Object> map = new HashMap<String,Object>();
        //code=0代表请求成功
        map.put("code",0);
        //返回的提示信息
        map.put("msg","");
        //count代表数据的总条数,前端会根据他计算出页码数
        map.put("count",count);
        //data代表我们的数据格式为数组
        map.put("data",list);
        return map;
    }

    @RequestMapping("/check")
    @ResponseBody
    public Message check(@RequestParam("questionId")Integer id){
        adminService.check(id);
        return new Message("success","审核成功");
    }

    /**
     * 删除文章
     * @param id
     */
    @RequestMapping("/remove")
    @ResponseBody
    public Message remove(@RequestParam("questionId")Integer id){
        adminQuestionService.remove(id);
        return new Message("success","删除成功");

    }
}
