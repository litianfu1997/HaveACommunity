package com.tech4flag.community.service;

import com.tech4flag.community.enums.CommentTypeEnum;
import com.tech4flag.community.exception.CustomizeErrorCode;
import com.tech4flag.community.exception.CustomizeException;
import com.tech4flag.community.mapper.CommentMapper;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.model.Comment;
import com.tech4flag.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-09 10:34
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @Transactional
    public void insert(Comment comment){
        if (comment.getParentId()==null||comment.getParentId()==0){
            //不存在该回复
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            //不存在该类型回复
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_ERROR);
        }
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())){
            //回复评论
            Comment dbComment = commentMapper.selectCommentByUserId(comment.getParentId());
            if (dbComment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question = questionMapper.selectQuestionById(comment.getParentId());
            if (question ==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //插入问题回复
            commentMapper.insert(comment);
            //回复问题数加一
            questionService.incComment(question.getId());
        }
//        commentMapper.insert(comment);

    }
}
