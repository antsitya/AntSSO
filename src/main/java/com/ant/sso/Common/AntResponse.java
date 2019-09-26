package com.ant.sso.Common;

import lombok.Data;

/**
 *  封装统一响应消息
 */
@Data
public class AntResponse {
    private AntResponseCode RespStatus;
    private Object RespObject;
    private int RespCode;
    private String RespMsg;
    public void setResp(AntResponseCode antResponseCode,Object respObject){
        this.RespStatus=antResponseCode;
        this.RespCode=antResponseCode.value();
        this.RespObject=respObject;
        this.RespMsg=antResponseCode.msg();
    }
    public void setError(AntResponseCode antResponseCode){
        this.RespStatus=antResponseCode;
        this.RespCode=antResponseCode.value();
        this.RespMsg=antResponseCode.msg();
    }
    public void setSuccess(Object respObject){
        this.RespStatus=AntResponseCode.SUCCESS_CODE;
        this.RespCode=AntResponseCode.SUCCESS_CODE.value();
        this.RespObject=respObject;
        this.RespMsg=AntResponseCode.SUCCESS_CODE.msg();
    }
}
