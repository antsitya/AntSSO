package com.ant.sso.Common;

import java.lang.reflect.Field;

public class BaseEntity<T> {
    public void notNull(String exMsg){
        Class clazz=this.getClass();
        Field fields[] = clazz.getDeclaredFields();
        boolean flag = true; //定义返回结果，默认为true
        for(Field field : fields){
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(this); //得到属性值
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if(fieldValue != null){  //只要有一个属性值不为null 就返回false 表示对象不为null
                flag = false;
                break;
            }
        }
        if(flag) throw new AntException(AntResponseCode.EMPTY_EXCEPTION,exMsg);
    }
}
