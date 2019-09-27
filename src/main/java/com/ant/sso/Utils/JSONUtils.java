package com.ant.sso.Utils;


import com.alibaba.fastjson.JSON;

/**
 * JSON工具类基于alibaba fastjson二次封装
 */
public class JSONUtils {
    /**
     *  普通对象转json字符串
     */
    public static String objToStr(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     *  json字符串转普通对象
     */
    public static <T> T strToObj(String jsonStr,Class<T> tClass){
        if(StringUtils.isEmpty(jsonStr)) return null;
        return JSON.toJavaObject(JSON.parseObject(jsonStr), tClass);
    }
}
