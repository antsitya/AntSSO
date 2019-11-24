package com.ant.sso.Config.aspect;

import com.ant.sso.Common.AntResponse;
import com.ant.sso.Common.AntResponseCode;
import com.ant.sso.Common.Operator;
import com.ant.sso.Common.annotation.Check;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Log4j
@Component
@Aspect
public class CheckAspect {

    @Pointcut("execution(public * com.ant.sso.Controller..*.*(..))&&@annotation(com.ant.sso.Common.annotation.Check)")//Controller包下及其子包的任意方法，不限参数类型
    public void check(){
        log.info("进入切入点 check ...");
    }

    @Around("check()")
    public Object checkAround(ProceedingJoinPoint point){
        Object obj=null;
        try{
            System.out.println("around check...");
            String errMsg=doCheck(point);
            if(errMsg!=null){
                AntResponse antResponse=new AntResponse();
                antResponse.setError(AntResponseCode.ILLEGAL_PARAMETER,errMsg);
                return antResponse;
            }else{
                obj=point.proceed();
            }
        }catch (Exception e){
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }

    private String doCheck(ProceedingJoinPoint point) throws NoSuchMethodException {
        Class<?> targetClass=point.getTarget().getClass();//获取方法所在Controller类的字节码对象
        MethodSignature ms= (MethodSignature) point.getSignature();//获取方法签名
        Method method=targetClass.getDeclaredMethod(ms.getName(),ms.getParameterTypes());//反射通过方法所在类的字节码对象获取方法对象
        Object[] params=point.getArgs();//获取请求参数
        Check check=method.getAnnotation(Check.class);//获取方法上的注解
        String[] values=check.value();//获取注解的值
        String[] paramterNames=ms.getParameterNames();
        Map<String,Object> paramters=new HashMap<>();
        for(int i=0;i<params.length;i++){
            paramters.put(paramterNames[i],params[i]);
        }
        String errMsg=null;
        for(String val:values){
            errMsg=resove(val,paramters);
            if(errMsg!=null) break;
        }
        return errMsg;
    }

    /**
     *  操作符 in,!=null,
     */
    private String resove(String val,Map<String,Object> paramMap){
        String msg=null;
        Boolean isVailded=true;
        String[] vals=val.split(":");
        if(val.contains(Operator.NOT_NULL.getValue())){
            msg=Operator.NOT_NULL.getErrMsg();
            String filedName=val.split(Operator.NOT_NULL.getValue())[0];
            if(paramMap.containsKey(filedName)){
                isVailded=Operator.NOT_NULL.getFun().apply(paramMap.get(filedName),null);
            }else{
                isVailded=false;
            }
        }
        if(vals.length==2) msg=vals[1];
        return isVailded?null:msg;
    }

}
