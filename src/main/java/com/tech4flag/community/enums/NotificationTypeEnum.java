package com.tech4flag.community.enums;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-17 12:54
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");


    private Integer status;
    private String name;




    NotificationTypeEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }
    public Integer getType() {
        return status;
    }

    public String getName() {
        return name;
    }
    public static  String nameOfType(Integer type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType().equals(type)){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }

}
