package com.lsj.luoapi.controller;

import com.lsj.luoapi.aop.Operator;
import com.lsj.luoapi.model.common.BaseResponse;
import com.lsj.luoapi.model.domain.RequestContext;
import com.lsj.luoapi.model.dto.user.UserDTO;
import com.lsj.luoapi.model.dto.user.UserLoginRequest;
import com.lsj.luoapi.model.dto.user.UserLoginResult;
import com.lsj.luoapi.model.dto.user.UserRegisterRequest;
import com.lsj.luoapi.service.user.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-center/user")
public class UserController extends BaseController{


    @Resource
    private UserService userService;


    @Operator(value = "UserLogin")
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResult loginResult = userService.doLogin(userLoginRequest);
        return BaseResponse.success(loginResult);
    }


    @PostMapping("/register")
    @Operator(value = "UserRegister")
    public BaseResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserDTO userDTO = userService.register(userRegisterRequest);
        return BaseResponse.success(userDTO);
    }


    @GetMapping("/me")
    @Operator(value = "GetCurrentUser")
    public BaseResponse getCurrentUser() {
        RequestContext context = getRequestContext();
        return BaseResponse.success(context.getUserDTO());
    }

  
}
