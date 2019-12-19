package com.tech4flag.community.model;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-18 16:42
 */
@Data
public class Message {
    private String status;
    private String msg;
    private Object data;

    public Message(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Message(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
