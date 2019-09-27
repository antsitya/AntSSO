package com.ant.sso.Common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 建造者模式重构Map对象
 */
@Data
public class AntMap {
    private Map<String,Object> res;
    public static AntMap build(){
        AntMap antMap=new AntMap();
        antMap.setRes(new HashMap<>());
        return antMap;
    }
    public AntMap put(String key,Object value){
        this.res.put(key, value);
        return this;
    }
    public AntMap remove(String key){
        this.res.remove(key);
        return this;
    }
}
