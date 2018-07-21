package com.smy.web;

/**
 * Created by smy on 2018/7/22.
 */
public interface IWebDeal {

    void doSomething() throws Exception;

    static void deal(int code, String message, IWebDeal deal) {
        try {
            deal.doSomething();
        } catch (Exception e) {
            throw new WebException(code, message, e);
        }
    }
}
