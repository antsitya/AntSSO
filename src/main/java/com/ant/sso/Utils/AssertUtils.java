package com.ant.sso.Utils;

import com.ant.sso.Common.AntException;
import com.ant.sso.Common.AntResponseCode;

/**
 * 工具类：断言检查
 */
public class AssertUtils {
   /* public static void notNull(Object object){
        if(object==null) throw new AntException(AntResponseCode.EMPTY_EXCEPTION);
    }

    public static void notNull(Object object,String exMsg){
        if(object==null) throw new AntException(AntResponseCode.EMPTY_EXCEPTION,exMsg);
    }*/

    public static <T> T notNull(T t){
        if(t==null) throw new AntException(AntResponseCode.EMPTY_EXCEPTION);
        return t;
    }
    public static <T> T notNull(T t,String exMsg){
        if(t==null) throw new AntException(AntResponseCode.EMPTY_EXCEPTION,exMsg);
        return t;
    }
    public static void check(boolean condition, String exMsg){
        if(!condition) throw new AntException(AntResponseCode.EXCEPTION_CODE,exMsg);
    }

    public static void check(boolean condition,AntResponseCode antResponseCode){
        if(!condition) throw new AntException(antResponseCode);
    }
}
