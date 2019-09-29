package com.ant.sso.Common;

import com.ant.sso.Utils.StringUtils;
import lombok.Data;

/**
 *  封装统一响应消息
 */
@Data
public class AntResponse {
    private Object RespObject;
    private int RespCode;
    private String RespMsg;
    public void setResp(AntResponseCode antResponseCode,Object respObject){
        this.RespCode=antResponseCode.value();
        this.RespObject=respObject;
        this.RespMsg=antResponseCode.msg();
    }
    public void setError(AntResponseCode antResponseCode){
        this.RespCode=antResponseCode.value();
        this.RespMsg=antResponseCode.msg();
    }
    public void setError(AntResponseCode antResponseCode,String exMsg){
        this.RespCode=antResponseCode.value();
        this.RespMsg= StringUtils.isEmpty(exMsg)?antResponseCode.msg():exMsg;
    }
    public void setSuccess(Object respObject){
        this.RespCode=AntResponseCode.SUCCESS_CODE.value();
        this.RespObject=respObject;
        this.RespMsg=AntResponseCode.SUCCESS_CODE.msg();
    }
    public void setResp(int respCode,String respMsg){
        this.RespCode=respCode;
        this.RespMsg=respMsg;
    }
    public void setResp(AntException antException){
        this.RespCode=antException.getExCode();
        this.RespMsg=antException.getExMsg();
    }
    public void setResp(AntException antException,String exMsg){
        this.RespCode=antException.getExCode();
        this.RespMsg=exMsg;
    }
}
