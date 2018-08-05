package com.smy.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by smy on 2018/7/20.
 */
public class WebUtil {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private static final String WHERE_BUILDER = "WhereBuilder";

    public WhereBuilder getWhereBuilder() {
        return getWhereBuilder(getRequest());
    }

    public WhereBuilder getWhereBuilder(HttpServletRequest request) {
        WhereBuilder whereBuilder = (WhereBuilder) request.getAttribute(WHERE_BUILDER);
        if (whereBuilder == null) {
            whereBuilder = new WhereBuilder();
            request.setAttribute(WHERE_BUILDER, whereBuilder);
        }
        return whereBuilder;
    }

}
