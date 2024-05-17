package com.lsj.luoapi.utils;

import com.lsj.luoapi.model.dto.user.UserDTO;

public class UserHodler {
    private static final ThreadLocal<UserDTO> threadLocal = new ThreadLocal<>();

    public static void save(UserDTO userDTO) {
        threadLocal.set(userDTO);
    }

    public static UserDTO get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
