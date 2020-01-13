package com.tech4flag.community.dto;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2020-01-13 12:09
 */
@Data
public class ChatDto {
    private Integer id;
    /**
     * 发送者id
     */
    private Integer fromUserId;
    /**
     * 接收者id
     */
    private Integer toUserId;

    private String msg;

    /**
     * 已读标志位 0代表未读，1代表已读
     */
    private Integer flag;

    private Long time;

    private String fromUserName;

    private String toUserName;
}
