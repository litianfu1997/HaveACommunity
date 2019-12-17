package com.tech4flag.community.model;

import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-12-17 17:05
 */
@Data
public class Admin {
    private Long id;
    private String username;
    private String password;
    private Long loginTime;

}
