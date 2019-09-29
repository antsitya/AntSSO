package com.ant.sso.Utils;

import com.ant.sso.Common.AntException;
import com.ant.sso.Common.AntResponseCode;

public class AssertUtils {
    public static void notNull(Object object){
        if(object==null) throw new AntException(AntResponseCode.EMPTY_EXCEPTION);
    }

    public static void notNull(Object object,String exMsg){
        if(object==null) throw new AntException(AntResponseCode.EMPTY_EXCEPTION,exMsg);
    }

    public static void check(boolean condition, String exMsg){
        if(!condition) throw new AntException(AntResponseCode.EXCEPTION_CODE,exMsg);
    }

    public static void check(boolean condition,AntResponseCode antResponseCode){
        if(!condition) throw new AntException(antResponseCode);
    }
}
