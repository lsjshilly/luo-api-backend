package com.lsj.luoapi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_api_info
 */
@TableName(value ="tb_api_info")
@Data
public class ApiInfo implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
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
    private Long userId;

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

    /**
     * 删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}