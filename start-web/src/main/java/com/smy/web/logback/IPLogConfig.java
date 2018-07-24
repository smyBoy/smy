package com.smy.web.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.smy.util.NetUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 日志打印IP信息，应用于微服务的日志收集
 * Created by smy on 2018/7/20.
 */
public class IPLogConfig extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        try {
            return NetUtil.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}