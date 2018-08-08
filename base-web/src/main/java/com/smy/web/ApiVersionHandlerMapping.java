package com.smy.web;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 基于spring扩展版本控制分发。
 *
 * @author smy
 */
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion version = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(version);
    }

    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion version) {
        return version == null ? null : new ApiVersionCondition(version.value());
    }

}
