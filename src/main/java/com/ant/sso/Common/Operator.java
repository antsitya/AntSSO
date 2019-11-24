package com.ant.sso.Common;

import com.ant.sso.Utils.StringUtils;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public enum Operator {
    GRATER_THAN(">",null,"请求参数异常!"),
    NOT_NULL("!=null",Operator::notNull,"请求参数异常：请求参数不为空!");
    private String value;
    private BiFunction<Object,String,Boolean> fun;
    private String errMsg;
    Operator(String value,BiFunction<Object,String,Boolean>fun,String msg){
        this.value=value;
        this.errMsg=msg;
        this.fun=fun;
    }
    private static Boolean notNull(Object value,String operatorNum){
        return value != null && ((!(value instanceof String)) || !StringUtils.isEmpty((String) value));
    }
}
