package com.lsj.luoapi.model.common;

import lombok.Data;

import java.util.List;

@Data
public class DeleteRequest<T> {
    private T id;

    /**
     * 批量删除
     */
    private List<T> ids;
}
