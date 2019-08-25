package com.tech4flag.community.dto;

import com.tech4flag.community.model.Question;
import lombok.Data;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-24 21:11
 */
@Data
public class QuestionFlag {
    private Integer userId;
    private Integer questionId;
    private Integer flag;

}
