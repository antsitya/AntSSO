package com.ant.sso.Common;

public class AntConstant {
    public static final String  LOGGER_PREFIX="error happen  on function ";
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";//正则表达式验证用户名
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";//正则表达式验证手机号
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//正则表达式验证邮箱
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";//正则表达式验证汉字
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";//正则表达式验证身份证
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";//正则表达式验证URL
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";//正则表达式验证IP地址
    public static final Integer LOGIN_EXPIRE_TIME=1000*60*30;//登录超时时间
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";//http请求用户身份验证
    private static final String DEFAULT_ENCODING = "UTF-8";//默认编码UTF-8
}
