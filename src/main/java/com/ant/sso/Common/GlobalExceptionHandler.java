package com.ant.sso.Common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局统一异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *  AntException异常类处理方法
     */
    @ExceptionHandler(value = AntException.class)
    public AntResponse antExceptionHandler(HttpServletRequest request,AntException antException) throws Exception{
        AntResponse antResponse=new AntResponse();
        antResponse.setResp(antException);
        return antResponse;
    }
}
