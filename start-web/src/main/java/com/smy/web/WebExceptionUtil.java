package com.smy.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by smy on 2018/7/22.
 */
@ControllerAdvice
@Slf4j
public class WebExceptionUtil {

    @Resource
    private HttpServletRequest request;

    @ExceptionHandler
    public BaseResult handlerException(Exception e) {
        if (e instanceof WebException) {
            WebException we = (WebException) e;
            int code = we.getCode();
            if (between(code, 400, 500)) {
                log.error(append(NetworkUtil.getIpAddress(request), request.getMethod(), request.getRequestURL().toString()), e);
            } else if (between(code, 500, 600)) {
                log.error(append(request.getRequestURI(), request.getParameterMap().toString()), e);
            } else if (code >= 600) {
                log.warn(e.getMessage());
            }
            return new BaseResult(code, e.getMessage());
        }
        log.error(append("非自定义异常:", request.getRequestURI(), request.getParameterMap().toString()), e);
        return new BaseResult(500, "系统错误");
    }

    private static boolean between(int code, int min, int max) {
        return code >= min && code < max;
    }

    public static String append(String... args) {
        if (args.length == 0) {
            return "";
        }
        if (args.length == 1) {
            return args[0];
        }
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg).append(" ");
        }
        return builder.toString();
    }
}
