package com.tech4flag.community.dto;

import com.tech4flag.community.model.School;
import com.tech4flag.community.model.User;
import lombok.Data;
import lombok.Getter;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-14 19:14
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
    private School school;



}
