package com.lsj.luoapi.controller;

import com.lsj.luoapi.aop.AuthChecker;
import com.lsj.luoapi.aop.Operator;
import com.lsj.luoapi.model.common.*;
import com.lsj.luoapi.model.domain.RequestContext;
import com.lsj.luoapi.model.dto.api.ApiAddRequest;
import com.lsj.luoapi.model.dto.api.ApiQueryRequest;
import com.lsj.luoapi.model.dto.api.ApiUpdateRequest;
import com.lsj.luoapi.model.dto.user.UserDTO;
import com.lsj.luoapi.model.dto.user.UserLoginRequest;
import com.lsj.luoapi.model.dto.user.UserLoginResult;
import com.lsj.luoapi.model.dto.user.UserRegisterRequest;
import com.lsj.luoapi.model.vo.ApiInfoVo;
import com.lsj.luoapi.service.api.ApiInfoService;
import com.lsj.luoapi.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/info")
public class ApiController extends BaseController{


    @Resource
    private ApiInfoService apiInfoService;


    @Operator(value = "AddApiInfo")
    @PostMapping("/add")
    public BaseResponse<AddOrUpdateResult> addApiInfo(@RequestBody ApiAddRequest apiAddRequest) {
        AddOrUpdateResult result = apiInfoService.addApiInfo(apiAddRequest);
        return BaseResponse.success(result);
    }


    @PostMapping("/update")
    @Operator(value = "UpdateApiInfo")
    public BaseResponse<AddOrUpdateResult> updateApiInfo(@RequestBody ApiUpdateRequest apiUpdateRequest) {
        AddOrUpdateResult result = apiInfoService.updateApiInfo(apiUpdateRequest);
        return BaseResponse.success(result);
    }


    @GetMapping("/api-infos")
    @Operator(value = "GetApiInfosByPage")
    @AuthChecker(anyRoles = {"user::common", "user::admin"})
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public BaseResponse<ListResult<ApiInfoVo>> getApiInfoPage(ApiQueryRequest apiQueryRequest, PageRequest pageRequest) {
        ListResult<ApiInfoVo> result = apiInfoService.getApiInfoPage(apiQueryRequest, pageRequest);
        return BaseResponse.success(result);
    }


    @GetMapping("/api-info")
    @Operator(value = "GetApiInfoById")
    @AuthChecker(anyRoles = {"user::admin", "user::common"})
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public BaseResponse<ApiInfoVo> getApiInfoById(@RequestBody IdRequest<Long> idRequest) {
        ApiInfoVo  apiInfoVo = apiInfoService.getApiInfoById(idRequest);
        return BaseResponse.success(apiInfoVo);
    }

    @DeleteMapping("/api-info")
    @Operator(value = "DeleteApiInfos")
    @AuthChecker(anyRoles = {"user::admin"})
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public BaseResponse<Void> deleteApiInfos(@RequestBody DeleteRequest<Long> deleteRequest) {
        apiInfoService.deleteApiInfos(deleteRequest);
        return BaseResponse.success();
    }

  
}
