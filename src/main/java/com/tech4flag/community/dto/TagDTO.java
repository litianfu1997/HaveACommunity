package com.tech4flag.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-08-16 10:36
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
