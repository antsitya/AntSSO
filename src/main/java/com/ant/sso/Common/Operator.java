package com.ant.sso.Common;

import com.ant.sso.Utils.StringUtils;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public enum Operator {
    GRATER_THAN(" > ",null,"请求参数异常!"),//大于
    GRATER_OR_EQUAL_TO(" [ ",null,"请求参数异常!"),//大于等于
    LESS_THAN(" < ",null,"请求参数异常!"),//小于
    LESS_OR_EQUAL_TO(" ] ",null,"请求参数异常!"),//小于等于
    NOT_NULL(" not null ",Operator::notNull,"请求参数异常：请求参数不为空!"),//不为空
    NOT_EQUAL(" != ",null,"请求参数异常!"),//不等于
    IN(" in ",null,"请求参数异常!"),//参数在几个数之间如 age in 1,2,3 等价于 age==1||age==2||age==3;
    NOT_IN(" not in ",null,"请求参数异常!"),//参数不在几个数之间
    BETWEEN_INCLUDE(" [] ",null,"请求参数异常!"),//在两个数范围之间(闭区间)
    BETWEEN_UN_INCLUDE(" () ",null,"请求参数异常!"),//在两个数范围之间(开区间)
    OUT_INCLUDE(" ][ ",null,"请求参数异常!"),//在闭区间之外
    OUT_UN_INCLUDE(" )( ",null,"请求参数异常!"),//在开区间之外
    ;
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
