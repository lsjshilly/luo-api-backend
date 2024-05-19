package com.lsj.luoapi.model.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiQueryRequest {

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
     * 0 关闭 1 开启
     */
    private Integer status;

    /**
     * 用户ID
     */
    private String username;

    /**
     * 描述信息
     */
    private String description;



}
