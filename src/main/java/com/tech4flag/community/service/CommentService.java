package com.tech4flag.community.service;

import com.tech4flag.community.dto.CommentDTO;
import com.tech4flag.community.enums.CommentTypeEnum;
import com.tech4flag.community.enums.NotificationStatusEnum;
import com.tech4flag.community.enums.NotificationTypeEnum;
import com.tech4flag.community.exception.CustomizeErrorCode;
import com.tech4flag.community.exception.CustomizeException;
import com.tech4flag.community.mapper.CommentMapper;
import com.tech4flag.community.mapper.NotificationMapper;
import com.tech4flag.community.mapper.QuestionMapper;
import com.tech4flag.community.mapper.UserMapper;
import com.tech4flag.community.model.Comment;
import com.tech4flag.community.model.Notification;
import com.tech4flag.community.model.Question;
import com.tech4flag.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;
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
            Comment parentComment = new Comment();
            Long parentId = comment.getParentId();
            parentComment.setId(Math.toIntExact(parentId));
            commentMapper.insert(comment);
            commentMapper.incCommentCount(parentComment);
            //创建通知
            createNotify(comment, dbComment.getCommentator(), NotificationTypeEnum.REPLY_COMMENT.getType());
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
            //创建通知
            createNotify(comment,question.getCreator(), NotificationTypeEnum.REPLY_QUESTION.getType());
        }
//        commentMapper.insert(comment);

    }

    private void createNotify(Comment comment, Integer receiver, Integer type) {
        //回复通知
        Notification notification =new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);
        //我回复的是谁的评论的id
        notification.setOuterId(Math.toIntExact(comment.getParentId()));
        //接收到通知的人
        notification.setNotifier(comment.getCommentator());
        //设置未读状态
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notificationMapper.insertNotify(notification);
    }

    public List<CommentDTO> selectCommentList(Integer parentId, Integer type) {
        List<Comment> comments = commentMapper.selectCommentListByParentId(Long.valueOf(parentId),type);
        if (comments.size()==0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> commentatorId = new ArrayList<>();
        commentatorId.addAll(commentators);
        List<User> userList = new ArrayList<>();
        for (int i=0;i<commentatorId.size();i++) {
            User user = userMapper.findById(commentatorId.get(i));
            userList.add(user);
        }

        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
