package com.tech4flag.community.dto;

import com.tech4flag.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private Long parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
}
