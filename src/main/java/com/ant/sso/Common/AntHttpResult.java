package com.ant.sso.Common;

public class AntHttpResult {
    private int code;
    private Object res;
    public AntHttpResult(int code,Object res){
        this.code=code;
        this.res=res;
    }
    public AntHttpResult(AntResponseCode antResponseCode){
        this.code=antResponseCode.value();
        this.res=antResponseCode.msg();
    }
}
