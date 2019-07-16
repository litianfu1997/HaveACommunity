package com.tech4flag.community.service;

import com.tech4flag.community.dto.PaginationDTO;
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
 * @date 2019-07-14 19:19
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount = questionMapper.count();
        if (page<1){
            page = 1;
        }
        if(page.equals(paginationDTO.getTotalPage())){
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.list(offset,size);



        for (Question question : questionList) {
           User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils可以将两个对象的属性进行快速封装
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setPagination(totalCount,page,size);
        return paginationDTO;
    }
}
