package com.ant.sso.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDUtils {
    /**
     *  获取一个UUID值
     * @return
     */
    public static   String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 获取多个UUID值
     * @param number 所需个数
     * @return UUID集合
     */
    public static List<String> getUUID(Integer number){
        List<String> list = new ArrayList<>();
        while (0 <= (number--)){
            list.add(getUUID());
        }
        return list;
    }
}
