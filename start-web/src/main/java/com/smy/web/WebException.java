package com.smy.web;

import lombok.Getter;

/**
 * Web异常统一封装。
 * 400——客户端错误，error，打印地址和uri
 * 500——服务器错误，error，打印请求参数异常
 * 600——业务级错误，warn
 * Created by smy on 2018/7/21.
 */
@Getter
public class WebException extends RuntimeException {
    /**
     * @see {@link BaseResult#getCode()}
     */
    private int code;

    public WebException(int code) {
        this.code = code;
    }

    public WebException(int code, String message) {
        super(message);
        this.code = code;
    }

    public WebException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public WebException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
