package com.smy.web.swagger;

import com.google.common.base.Predicate;
import com.smy.web.version.ApiVersion;
import lombok.AllArgsConstructor;
import springfox.documentation.RequestHandler;

/**
 * swagger版本扫描选择器。
 * 可以扫描{@link ApiVersion}
 *
 * @author smy
 */
@AllArgsConstructor
public class ApiSwaggerPredicate implements Predicate<RequestHandler> {

    private int apiVersion;

    @Override
    public boolean apply(RequestHandler requestHandler) {
        ApiVersion apiVersion = requestHandler.findControllerAnnotation(ApiVersion.class).orNull();
        if (apiVersion != null && apiVersion.value() == this.apiVersion) {
            return true;
        }
        return false;
    }
}
