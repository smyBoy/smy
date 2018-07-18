package com.smy.web;

import com.google.common.base.Predicate;
import lombok.AllArgsConstructor;
import springfox.documentation.RequestHandler;

/**
 * Created by smy on 2018/6/10.
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
