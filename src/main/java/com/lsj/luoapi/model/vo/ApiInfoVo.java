package com.lsj.luoapi.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
     * 请求头
     */
    private String requestHeaders;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 响应数据
     */
    private String responseBody;

    /**
     * 请求示例
     */
    private String requestExample;

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
