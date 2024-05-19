package com.lsj.luoapi.model.dto.api;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiUpdateRequest {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * api名称
     */
    private String name;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求信息
     */
    private String request;

    /**
     * 响应信息
     */
    private String response;



    /**
     * 描述信息
     */
    private String description;


}
