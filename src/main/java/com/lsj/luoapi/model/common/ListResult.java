package com.lsj.luoapi.model.common;


import lombok.Data;

import java.util.List;

@Data
public class ListResult<T> {
    private List<T> items;
    private Long total;

}
