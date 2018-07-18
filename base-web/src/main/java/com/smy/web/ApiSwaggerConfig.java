package com.smy.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2018/6/11.
 */
@ConfigurationProperties(prefix = "api")
@EnableSwagger2
@Slf4j
public class ApiSwaggerConfig implements InitializingBean, BeanFactoryAware {

    private static List<Parameter> parameters = new ArrayList<>();

    private DefaultListableBeanFactory beanFactory;

    private List<ApiSwaggerData> swagger;

    public static Parameter head(String name, String description) {
        return new ParameterBuilder()
                .name(name)
                .description(description)
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .build();
    }

    private static Parameter api(int version) {
        return new ParameterBuilder()
                .name("version")
                .description("版本号")
                .parameterType("path")
                .modelRef(new ModelRef("string"))
                .required(true)
                .defaultValue("v" + version)
                .build();
    }

    public static void add(Parameter parameter) {
        parameters.add(parameter);
    }

    public void setSwagger(List<ApiSwaggerData> swagger) {
        this.swagger = swagger;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("加载api接口文档");
        if (CollectionUtils.isEmpty(swagger)) {
            log.info("加载默认RestController接口");
            beanFactory.registerSingleton("swagger0", createRestApi());
        } else {
            for (ApiSwaggerData data : swagger) {
                log.info("api=" + data);
                beanFactory.registerSingleton("swagger" + data.getVersion(), createRestApi(data));
            }
        }
    }

    private Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .apiInfo(new ApiInfoBuilder().title("Restful API").build());
    }

    private Docket createRestApi(ApiSwaggerData data) {
        List<Parameter> list = new ArrayList<>(parameters);
        list.add(api(data.getVersion()));
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("v" + data.getVersion())
                .select()
                .apis(new ApiSwaggerPredicate(data.getVersion()))
                .build()
                .globalOperationParameters(list)
                .apiInfo(apiInfo(data));
    }

    private ApiInfo apiInfo(ApiSwaggerData data) {
        return new ApiInfoBuilder().
                title(data.getTitle()).
                description(data.getDescription())
                .version("v" + data.getVersion())
                .build();
    }
}
