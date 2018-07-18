package com.smy.web;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smy on 2018/5/23.
 */
@AllArgsConstructor
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    // 路径中版本的前缀， 这里用 /v[0-9]+的形式
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)");

    public static int findVersion(String path) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(path);
        return m.find() ? Integer.valueOf(m.group(1)) : -1;
    }

    private int apiVersion;

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.apiVersion);
    }

    @Nullable
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        int version = findVersion(request.getRequestURI());
        return version >= this.apiVersion ? this : null;
    }


    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest httpServletRequest) {
        // 优先匹配最新的版本号
        return other.apiVersion - this.apiVersion;
    }
}
