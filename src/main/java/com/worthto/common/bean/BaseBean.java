package com.worthto.common.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenjie on 15/7/3.
 */
public class BaseBean implements Serializable{
    public static final String ID = "_id";
    public static final String STATUS_OK = "0";
    public static final String STATUS_FAIL = "-1";

    @Id
    private String id;

    /**
     * 以下属性只在关联查询时有用，数据库中不保存
     */
    @Transient
    private Map<String, Object> extMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    /**
     * 返回查询字段值，注意不是线程安全
     * @param key
     */
    public Object getExtValue(String key) {
        return extMap != null ? extMap.get(key) : null;
    }

    /**
     * 设置查询字段值，注意不是线程安全
     * @param key
     * @param value
     */
    public void setExtValue(String key, Object value) {
        if (extMap == null) {
            extMap = new HashMap<String, Object>();
        }
        extMap.put(key, value);
    }

    /**
     * 设置查询字段值，注意不是线程安全
     * @param map
     */
    public void putAllExtValue(Map<String, Object> map) {
        if (map != null) {
            if (extMap == null) {
                extMap = new HashMap<String, Object>();
            }

            extMap.putAll(map);
        }
    }
}
