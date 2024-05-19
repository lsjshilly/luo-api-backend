package com.lsj.luoapi.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsj.luoapi.model.common.AddOrUpdateResult;
import com.lsj.luoapi.model.dto.user.UserDTO;
import com.lsj.luoapi.model.dto.user.UserLoginRequest;
import com.lsj.luoapi.model.dto.user.UserLoginResult;
import com.lsj.luoapi.model.dto.user.UserRegisterRequest;
import com.lsj.luoapi.model.entity.User;

/**
 * @author liushijie
 * @description 针对表【tb_user】的数据库操作Service
 * @createDate 2024-05-07 23:21:15
 */
public interface UserService extends IService<User> {

    UserLoginResult doLogin(UserLoginRequest userLoginRequest);

    AddOrUpdateResult register(UserRegisterRequest userRegisterRequest);

    UserDTO getLoginUser(long userId);
}
