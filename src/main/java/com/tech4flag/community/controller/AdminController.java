package com.tech4flag.community.controller;

import com.tech4flag.community.dto.AdminQuestionDTO;
import com.tech4flag.community.model.Question;
import com.tech4flag.community.service.AdminQuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("/admin")
    public String AdminIndex(){
        return "adminView";
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

    @RequestMapping("/remove")
    @ResponseBody
    public void remove(@RequestParam("questionId")Integer id){
        adminQuestionService.remove(id);
        System.out.println("进入删除控制层："+id);
    }
}
