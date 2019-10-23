package com.tech4flag.community.service;

import com.tech4flag.community.dto.AdminQuestionDTO;
import com.tech4flag.community.dto.QuestionDTO;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.mapper.UserMapper;
import com.tech4flag.community.model.Question;
import com.tech4flag.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private UserMapper userMapper;

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

    public Integer count() {
        Integer count = questionMapper.count();
        return count;
    }
}
