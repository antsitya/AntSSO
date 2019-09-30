package com.ant.sso.Common;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

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

    /**
     *  请求参数异常处理方法
     *  BindException.class：处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     *  MethodArgumentNotValidException.class：处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     *  MethodArgumentNotValidException.class：处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class})
    public AntResponse MethodArgumentNotValidHandler(Exception exception) throws Exception{
        AntResponse antResponse =new AntResponse();
        String errMsg=null;
        if(exception instanceof BindException){
            BindingResult bindingResult=((BindException) exception).getBindingResult();
            FieldError fieldError=bindingResult.getFieldError();
            errMsg=fieldError!=null?fieldError.getDefaultMessage():null;
        }else if(exception instanceof MethodArgumentNotValidException){
            BindingResult bindingResult= ((MethodArgumentNotValidException) exception).getBindingResult();
            FieldError fieldError=bindingResult.getFieldError();
            errMsg=fieldError!=null?fieldError.getDefaultMessage():null;
        }else if(exception instanceof ConstraintViolationException){
            errMsg=((ConstraintViolationException) exception).getConstraintViolations().iterator().next().getMessage();
        }
        antResponse.setError(AntResponseCode.ILLEGAL_PARAMETER,errMsg);
        return antResponse;
    }
}
