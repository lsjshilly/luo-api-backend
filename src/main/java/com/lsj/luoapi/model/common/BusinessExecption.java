package com.lsj.luoapi.model.common;


import lombok.Getter;

@Getter
public class BusinessExecption extends RuntimeException {
    private final int code;


    public BusinessExecption(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessExecption(ErrCode errCode, String message) {
        super(message);
        this.code = errCode.getCode();

    }
}
