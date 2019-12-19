package com.tech4flag.community.dto;

import com.tech4flag.community.model.School;
import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-19 14:57
 */
@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private String bio;
    private String tag;
    private Integer schoolId;
    private String sex;
    private Integer age;
    private School school;
}
