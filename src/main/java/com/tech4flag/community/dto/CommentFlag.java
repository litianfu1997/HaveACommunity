package com.tech4flag.community.dto;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-24 21:11
 */
@Data
public class CommentFlag {
    private Integer userId;
    private Integer commentId;
    private Integer flag;

}
