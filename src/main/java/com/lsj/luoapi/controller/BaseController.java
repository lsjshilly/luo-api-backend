package com.lsj.luoapi.controller;

import com.lsj.luoapi.model.domain.RequestContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

import static com.lsj.luoapi.model.constant.SystemConstants.DEFAULT_REQUST_ATTRIBUTES;

public abstract class BaseController {
    protected RequestContext getRequestContext() {
        RequestAttributes requestAttributes = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        return (RequestContext) requestAttributes.getAttribute(DEFAULT_REQUST_ATTRIBUTES, RequestAttributes.SCOPE_REQUEST);
    }
}
