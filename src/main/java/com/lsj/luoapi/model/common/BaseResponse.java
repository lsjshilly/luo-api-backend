package com.lsj.luoapi.model.common;


import lombok.Data;

@Data
public class BaseResponse<T> {

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;


    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public static <T> BaseResponse<T> success() {
        return new BaseResponse<T>(ErrCode.SUCCESS.getCode(), "", null);
    }


    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(ErrCode.SUCCESS.getCode(), "", data);
    }


    public static <T> BaseResponse<T> error(int code, String errmsg) {
        return new BaseResponse<T>(code, errmsg, null);
    }

    public static <T> BaseResponse<T> error(ErrCode errCode) {
        return new BaseResponse<T>(errCode.getCode(), errCode.getMessage(), null);
    }


    public static <T> BaseResponse<T> error(ErrCode errCode, String errmsg) {
        return new BaseResponse<T>(errCode.getCode(), errmsg, null);
    }


}
