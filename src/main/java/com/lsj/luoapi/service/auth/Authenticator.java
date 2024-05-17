package com.lsj.luoapi.service.auth;

import com.lsj.luoapi.model.common.BaseResponse;
import com.lsj.luoapi.model.domain.RequestContext;

public interface Authenticator {

    BaseResponse validate(RequestContext ctx);


    BaseResponse authenticate(RequestContext ctx);
}
