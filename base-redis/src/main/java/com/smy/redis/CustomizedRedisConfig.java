package com.smy.redis;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author smy
 */
@ConfigurationProperties(prefix = "spring.redis")
@Setter
@Slf4j
public class CustomizedRedisConfig implements InitializingBean, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;

    private String host;
    private Integer port;
    private String password;
    private Integer database = 0;
    private Map<String, CustomizedRedisConfig> customized;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(customized)) {
            return;
        }
        log.info("启动redisTemplate定制化配置");
        customized.forEach((k, v) -> {
            copy(v, this);
            beanFactory.registerSingleton(k, redisTemplate(v));
            log.info("定制redisTemplate:" + k);
        });
    }

    private RedisTemplate redisTemplate(CustomizedRedisConfig config) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(config.host, config.port);
        configuration.setDatabase(config.database);
        if (config.password != null) {
            configuration.setPassword(RedisPassword.of(config.password));
        }
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        factory.afterPropertiesSet();
        RedisTemplate template = new RedisTemplate<>();
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(factory);
        template.afterPropertiesSet();
        return template;
    }

    private void copy(CustomizedRedisConfig config, CustomizedRedisConfig base) {
        if (config.host == null) {
            config.host = base.host;
        }
        if (config.port == null) {
            config.port = base.port;
        }
        if (config.password == null) {
            config.password = base.password;
        }
        if (config.database == null) {
            config.database = base.database;
        }
    }
}
