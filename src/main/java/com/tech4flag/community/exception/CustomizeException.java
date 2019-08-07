package com.tech4flag.community.exception;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-07 11:19
 */
public class CustomizeException extends RuntimeException{
    private String message;
    public CustomizeException(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
