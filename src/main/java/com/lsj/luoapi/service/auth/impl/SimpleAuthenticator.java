package com.lsj.luoapi.service.auth.impl;

import cn.hutool.json.JSONUtil;
import com.lsj.luoapi.model.common.BaseResponse;
import com.lsj.luoapi.model.common.ErrCode;
import com.lsj.luoapi.model.domain.RequestContext;
import com.lsj.luoapi.service.auth.Authenticator;

import java.util.List;

public class SimpleAuthenticator implements Authenticator {
    @Override
    public BaseResponse<Void> validate(RequestContext ctx) {
        return null;
    }

    @Override
    public BaseResponse<Void> authenticate(RequestContext ctx) {

        List<String> anyRoles = ctx.getOperation().getAnyRoles();
        List<String> mustRoles = ctx.getOperation().getMustRoles();


        if (anyRoles.isEmpty() && mustRoles.isEmpty()) {
            return null;
        }

        if (ctx.getUserDTO() == null) {
            return BaseResponse.error(ErrCode.ERR_NOT_LOGIN, "用户未登录");
        }

        String roles = ctx.getUserDTO().getRoles();
        List<String> rolesList = JSONUtil.toList(roles, String.class);

        if (!anyRoles.isEmpty()) {
            long count = anyRoles.stream().filter(rolesList::contains).count();
            if (count == 0) {
                return BaseResponse.error(ErrCode.ERR_ACCESS_DENEY, "无权限");
            }
        }

        if (!mustRoles.isEmpty()) {
            long count = mustRoles.stream().filter(rolesList::contains).count();
            if (count != mustRoles.size()) {
                return BaseResponse.error(ErrCode.ERR_ACCESS_DENEY, "无权限");
            }
        }

        return null;
    }
}
