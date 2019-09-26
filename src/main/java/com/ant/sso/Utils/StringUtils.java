package com.ant.sso.Utils;

import com.ant.sso.Common.AntSysConstant;

import java.util.regex.Pattern;

/**
 *  字符串操作工具类
 */
public class StringUtils {
    /**
     *  是否为空
     */
    public static Boolean isEmpty(String str){
        return str==null||str.length()==0;
    }

    /**
     *  首字母大写
     */
    public static String UpFirst(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     *  去除空格
     */
    public static String clearBlank(String str){
        return str.replace(" ","");
    }

    /**
     *  手机号校验
     */
    public static boolean isCellphone(String str){return Pattern.matches(AntSysConstant.REGEX_MOBILE,str);}

    /**
     *  邮箱校验
     */
    public static boolean isEmail(String str){return Pattern.matches(AntSysConstant.REGEX_EMAIL,str);}

    /**
     *  用户名校验
     */
    public static boolean isUserName(String str){return Pattern.matches(AntSysConstant.REGEX_USERNAME,str);}
}
