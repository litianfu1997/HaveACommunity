package com.tech4flag.community.service;

import com.tech4flag.community.exception.CustomizeErrorCode;
import com.tech4flag.community.exception.CustomizeException;
import com.tech4flag.community.model.Comment;
import org.springframework.stereotype.Service;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-09 10:34
 */
@Service
public class CommentService {
    public void insert(Comment comment){
        if (comment.getParentId()==null||comment.getParentId()==0){
            //不存在该回复
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

    }
}
