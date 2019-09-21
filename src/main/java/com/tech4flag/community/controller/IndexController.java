package com.tech4flag.community.controller;

import com.tech4flag.community.cache.HotTagCache;
import com.tech4flag.community.dto.PaginationDTO;
import com.tech4flag.community.dto.QuestionDTO;
import com.tech4flag.community.dto.QuestionFlag;
import com.tech4flag.community.mapper.QuestionLikeCountMapper;
import com.tech4flag.community.service.NotificationService;
import com.tech4flag.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-08 22:53
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "10") Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        @RequestParam(name = "tag",required = false) String tag,
                        @RequestParam(name = "type",required = false) String type) {

        PaginationDTO<QuestionDTO> pagination = questionService.list(type,search,tag,page,size);
        List<String> tags = hotTagCache.getHots();
        if (StringUtils.isNotBlank(type)){
            switch (type){
                case "hot":
                    model.addAttribute("type",type);
                    break;
                case "hotweek":
                    model.addAttribute("type",type);
                    break;
                case "noreply":
                    model.addAttribute("type",type);
                    break;
            }
        }
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("tags",tags);
        model.addAttribute("tag",tag);

        return "index";
    }
}
