package com.tech4flag.community.service;

import com.tech4flag.community.dto.AdminQuestionDTO;
import com.tech4flag.community.mapper.QuestionLikeCountMapper;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.mapper.UserMapper;
import com.tech4flag.community.model.Question;
import com.tech4flag.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-10-04 11:39
 */
@Service
public class AdminQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionLikeCountMapper questionLikeCountMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 已审核的问题列表
     * @param page
     * @param limit
     * @return
     */
    public List<AdminQuestionDTO> list(Integer page,Integer limit) {
        List<AdminQuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = questionMapper.list(page, limit);
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            AdminQuestionDTO adminQuestionDTO = new AdminQuestionDTO();
            BeanUtils.copyProperties(question,adminQuestionDTO);
            adminQuestionDTO.setName(user.getName());
            questionDTOS.add(adminQuestionDTO);
        }
        return questionDTOS;
    }

    /**
     * 未审核的文章列表
     * @param page
     * @param limit
     * @return
     */
    public List<AdminQuestionDTO> uncheckList(Integer page,Integer limit) {
        List<AdminQuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = questionMapper.uncheckList(page, limit);
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            AdminQuestionDTO adminQuestionDTO = new AdminQuestionDTO();
            BeanUtils.copyProperties(question,adminQuestionDTO);
            adminQuestionDTO.setName(user.getName());
            questionDTOS.add(adminQuestionDTO);
        }
        return questionDTOS;
    }

    public Integer count() {
        Integer count = questionMapper.count();
        return count;
    }

    public Integer unCount() {
        Integer count = questionMapper.unCount();
        return count;
    }
    public Integer allCount() {
        Integer count = questionMapper.allCount();
        return count;
    }
    @Transactional
    public void remove(Integer id) {
        //先删除子表依赖
        questionLikeCountMapper.removeById(id);
        //再删除主表数据
        questionMapper.removeById(id);
    }

    public List<AdminQuestionDTO> allQuestionList(Integer page, Integer limit) {
        List<AdminQuestionDTO> questionDTOS = new ArrayList<>();
        List<Question> questions = questionMapper.allQuestionList(page, limit);
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            AdminQuestionDTO adminQuestionDTO = new AdminQuestionDTO();
            BeanUtils.copyProperties(question,adminQuestionDTO);
            adminQuestionDTO.setName(user.getName());
            questionDTOS.add(adminQuestionDTO);
        }
        return questionDTOS;
    }
}
