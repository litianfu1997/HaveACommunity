package com.tech4flag.community.enums;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-17 13:05
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1)
    ;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }}
