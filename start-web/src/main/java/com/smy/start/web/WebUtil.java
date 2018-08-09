package com.smy.start.web;

import com.smy.orm.WhereBuilder;
import com.smy.web.HttpServletUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * web工具。
 *
 * @author smy
 */
public class WebUtil {

    private static final String WHERE_BUILDER = "WhereBuilder";

    public WhereBuilder getWhereBuilder() {
        return getWhereBuilder(HttpServletUtil.getRequest());
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
