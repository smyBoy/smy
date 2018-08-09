package com.smy.web.exception;

import lombok.Getter;

/**
 * Web异常统一封装。
 *
 * @author smy
 */
@Getter
public class WebException extends RuntimeException {
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
