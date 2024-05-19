package com.lsj.luoapi.model.common;

import lombok.Data;

import java.util.List;

@Data
public class PageRequest {
    /**
     * 当前页
     */
    private  long pageNum = 1;

    /**
     * 也大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private List<String> sortFields;


}
