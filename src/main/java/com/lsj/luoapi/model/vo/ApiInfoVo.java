package com.lsj.luoapi.model.vo;

import com.lsj.luoapi.model.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiInfoVo {

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
     * 0 关闭 1 开启
     */
    private Integer status;

    /**
     * 用户ID
     */
    private UserDTO userInfo;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
