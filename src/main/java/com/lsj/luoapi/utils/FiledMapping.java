package com.lsj.luoapi.utils;

import java.util.HashMap;
import java.util.Map;

public class FiledMapping {

    public static final Map<String, String> DATABASE_FILED_MAPPING = new HashMap<>();

    static {
        DATABASE_FILED_MAPPING.put("createTime", "create_time");
        DATABASE_FILED_MAPPING.put("updateTime", "update_time");
        DATABASE_FILED_MAPPING.put("userId", "user_id");
    }


    public static String getDatabaseFiled(String originFiled) {
        return DATABASE_FILED_MAPPING.getOrDefault(originFiled, originFiled);
    }
    
}
