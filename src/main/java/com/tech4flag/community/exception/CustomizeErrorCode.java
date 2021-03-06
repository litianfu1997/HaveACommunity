package com.tech4flag.community.exception;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-09 10:47
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你要找的问题不见了，要不换一个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试！"),
    SYS_ERROR(2004,"服务器冒烟了，要不然您稍后试试!!!"),
    TYPE_PARAM_ERROR(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复不存在或者被删除了"),
    COMMENT_NOT_NULL(2007,"回复内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008,"兄弟，你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009,"消息不见了"),
    FILE_UPLOAD_FAIL(2010,"文件上传失败，请重试哦")

    ;
    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
