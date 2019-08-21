package com.tech4flag.community.service;

import com.tech4flag.community.dto.PaginationDTO;
import com.tech4flag.community.dto.QuestionDTO;
import com.tech4flag.community.exception.CustomizeErrorCode;
import com.tech4flag.community.exception.CustomizeException;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.mapper.UserMapper;
import com.tech4flag.community.model.Question;
import com.tech4flag.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-14 19:19
 */
@Service
@Transactional
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    private Integer totalCount;

    private List<Question> questionList;

    public PaginationDTO<QuestionDTO> list(String search,Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        Integer totalPage;

        if (StringUtils.isNotBlank(search)){
            //将空格全部替换为 '|'
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("%"));
            search = "%"+search+"%";
            totalCount = questionMapper.countByTags(search);
        }else {
            totalCount = questionMapper.count();
        }

        if (totalCount==0){
            totalPage=1;
        }else if (totalCount%size==0&&totalCount!=0){
            totalPage = totalCount/size;
        }else {
            totalPage = (totalCount/size)+1;
        }

        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);
        if (StringUtils.isNotBlank(search)){

            questionList = questionMapper.listByTags(search,offset,size);
        }else {
             questionList = questionMapper.list(offset,size);
             if (questionList==null){
                 return null;
             }
        }

        for (Question question : questionList) {
           User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils可以将两个对象的属性进行快速封装
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        Collections.sort(questionDTOList);
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(questionDTOList);
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
        if (totalCount%size==0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size+1;
        }

        if (page<1){
            page = 1;
        }
        if (page>totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.listByUserId(userId,offset,size);
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils可以将两个对象的属性进行快速封装
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        //BeanUtils可以将两个对象的属性进行快速封装
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
    public List<QuestionDTO> getRelevantQuestion(Integer id) {
        Question question = questionMapper.getById(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        //\W:匹配非字母、数字、下划线。等价于 '[^A-Za-z0-9_]'
        String tagRegexp = question.getTag().replaceAll(",", "|");
        List<Question> questionList = questionMapper.relevantQuestion(id, tagRegexp);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //BeanUtils可以将两个对象的属性进行快速封装
        for (Question question1 : questionList) {
            QuestionDTO questionDTO =new QuestionDTO();
            BeanUtils.copyProperties(question1,questionDTO);
            User user = userMapper.findById(question1.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.create(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            Integer update = questionMapper.update(question);
            if (update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 阅读数增加
     * @param id
     */
    public void incView(Integer id) {
        Question question = questionMapper.getById(id);
        questionMapper.updateViewCount(question);
    }

    /**
     * 回复数增加
     * @param id
     */
    public void incComment(Integer id){
        Question question = questionMapper.getById(id);
        questionMapper.updateCommentCount(question);
    }
}
