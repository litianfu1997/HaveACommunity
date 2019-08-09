package com.tech4flag.community.exception;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-07 11:19
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;
    public CustomizeException(String message){
        this.message=message;
    }

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
