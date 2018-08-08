package com.smy.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * swagger数据描述。
 *
 * @author smy
 */
@Getter
@Setter
@ToString
public class ApiSwaggerData {
    private int version;
    private String title = "";
    private String description = "";

}
