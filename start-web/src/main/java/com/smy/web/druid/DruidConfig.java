package com.smy.web.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by smy on 2018/7/19.
 */
@ConfigurationProperties(prefix = "druid.monitor")
@Configuration
public class DruidConfig {

    private String allow = "";
    private String deny = "";
    private String username = "druid";
    private String password = "11``qqq";
    private String reset = "false";
    private String exclusions = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid*,/actuator/*,/v2/api-docs,/swagger*";

    /**
     * 注册一个StatViewServlet
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow", allow);
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", deny);
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", username);
        servletRegistrationBean.addInitParameter("loginPassword", password);
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", reset);
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", exclusions);
        return filterRegistrationBean;
    }

    @Bean(name = "druid-stat-interceptor")
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    @Bean
    @ConditionalOnProperty(value = "druid.monitor.spring", havingValue = "true")
    public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator() {
        //必须使用接口注入才能被监控
        BeanTypeAutoProxyCreator creator = new BeanTypeAutoProxyCreator();
        creator.setTargetBeanType(DruidMonitor.class);
        creator.setInterceptorNames("druid-stat-interceptor");
        return creator;
    }

}
