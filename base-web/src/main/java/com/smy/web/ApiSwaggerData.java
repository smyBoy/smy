package com.smy.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by smy on 2018/6/11.
 */
@Getter
@Setter
@ToString
public class ApiSwaggerData {
    private int version;
    private String title = "";
    private String description = "";

}
