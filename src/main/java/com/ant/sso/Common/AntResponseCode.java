package com.ant.sso.Common;

public enum  AntResponseCode {
    SUCCESS_CODE(200,"请求成功"),
    EXCEPTION_CODE(400,"请求异常"),
    ILLEGAL_PARAMETER(401,"请求参数异常"),
    EMPTY_EXCEPTION(402,"空异常"),
    REDIS_EXCEPTION_CODE(403,"Redis操作异常"),
    SERVER_EXCEPTION(500,"服务器异常"),
    HTTP_EXCEPTION(501,"远程接口调用异常"),
    HTTP_SERVER_EXCEPTION(502,"远程接口服务器异常"),
    USER_NOT_EXIST(600,"用户不存在"),
    LOGIN_PASSWORD_ERROR(601,"用户名或密码错误"),
    USER_NOT_LOGIN(602,"用户未登录");
    private int value;
    private String msg;
    public int value(){return this.value;}
    public String msg(){return this.msg;}
    AntResponseCode(int value){ this.value=value; }
    AntResponseCode(int value,String msg){ this.value=value;this.msg=msg; }
}
