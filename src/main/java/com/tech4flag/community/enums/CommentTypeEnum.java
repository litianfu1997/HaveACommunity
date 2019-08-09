package com.tech4flag.community.enums;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-09 10:30
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2)
    ;
    private Integer type;
    CommentTypeEnum(Integer type){
        this.type=type;
    }

    public Integer getType() {
        return type;
    }}
