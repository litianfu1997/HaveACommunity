package com.tech4flag.community.model;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-17 12:47
 */
@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerId;
    private Integer type;
    private Long    gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}
