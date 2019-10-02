package com.ant.sso.Common;

import lombok.Data;

/**
 *  Ant自定义异常基类
 */
@Data
public class AntException extends RuntimeException {
    private int exCode;
    private String exMsg;
    public AntException(){
        super(AntResponseCode.EXCEPTION_CODE.msg());
        this.exCode=AntResponseCode.EXCEPTION_CODE.value();
        this.exMsg=AntResponseCode.EXCEPTION_CODE.msg();
    }
    public AntException(int exCode,String exMsg){
        super(exMsg);
        this.exCode=exCode;
        this.exMsg=exMsg;
    }
    public AntException(AntResponseCode antResponseCode){
        super(antResponseCode.msg());
        this.exCode=antResponseCode.value();
        this.exMsg=antResponseCode.msg();
    }
    public AntException(String exMsg){
        super(exMsg);
        this.exCode=AntResponseCode.EXCEPTION_CODE.value();
        this.exMsg=exMsg;
    }
    public AntException(AntResponseCode antResponseCode,String exMsg){
        super(exMsg);
        this.exCode=antResponseCode.value();
        this.exMsg=exMsg;
    }
}
