package com.smy.web.exception;

/**
 * web接口处理方法,抓取WebException异常。
 *
 * @author smy
 */
public interface WebMethod {
    /**
     * 执行方法。
     *
     * @throws Exception 异常
     */
    void doSomething() throws Exception;

    /**
     * 异常处理统一封装。
     *
     * @param code    异常编号
     * @param message 异常信息
     * @param method  执行方法
     */
    static void deal(int code, String message, WebMethod method) {
        try {
            method.doSomething();
        } catch (Exception e) {
            if (e instanceof WebException) {
                throw (WebException) e;
            } else {
                throw new WebException(code, message, e);
            }
        }
    }
}
