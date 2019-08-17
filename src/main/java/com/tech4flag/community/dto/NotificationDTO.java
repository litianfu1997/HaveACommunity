package com.tech4flag.community.dto;

import com.tech4flag.community.model.User;
import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-17 13:43
 */
@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer questionId;
    private Integer status;
    private Integer notifier;
    private String notifierName;
    private String outerTitle;
    private String type;
}
