package com.ant.sso.Config.aspect;

import com.ant.sso.Common.AntException;
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
     *  操作符
     *  in ：参数在几个数之间如 age in 1,2,3 等价于 age==1||age==2||age==3;
     *  not in : 参数不在几个数之间
     *  not null：参数不为空;
     *  != : 参数不为;
     *  > ：参数大于;
     *  < ：参数小于;
     *  [ : 参数大于等于
     *  ] ：参数小于等于
     *  [] : 参数在两个数范围之间(闭区间) 如 age [] 19,25 等价于 age>=19&&age<=25
     *  () : 参数在两个数范围之间(开区间) 如 age () 19,25 等价于 age>19&&age<25
     *  ][ : 参数在闭区间之外
     *  )( : 参数在开区间之外
     */
    private String resove(String val,Map<String,Object> paramMap){
        Boolean isVailded=true;
        Operator operator=getOperator(val);
        if(operator==null) throw new AntException(AntResponseCode.CHECK_RULES_EXCEPTION);
        String[] vals=val.split(":");
        String msg=vals.length>1?vals[1]:operator.getErrMsg();
        String[] checkParams=vals[0].split(operator.getValue());
        String filedName=checkParams[0];
        String checkValue=checkParams.length>1?checkParams[1]:null;
        return !paramMap.containsKey(filedName)?msg:operator.getFun().apply(paramMap.get(filedName),checkValue)?null:msg;
        /*if(paramMap.containsKey(filedName)){
            isVailded=operator.getFun().apply(paramMap.get(filedName),checkValue);
        }else{
            isVailded=false;
        }*/
        /*if(val.contains(Operator.NOT_NULL.getValue())){
            msg=Operator.NOT_NULL.getErrMsg();
            String filedName=val.split(Operator.NOT_NULL.getValue())[0];
            if(paramMap.containsKey(filedName)){
                isVailded=Operator.NOT_NULL.getFun().apply(paramMap.get(filedName),null);
            }else{
                isVailded=false;
            }
        }*/
//        return isVailded?null:msg;
    }

    private Operator getOperator(String checkStr){
        Operator[] operators=Operator.values();
        for(Operator operator:operators){
            if(checkStr.contains(operator.getValue()))
                return operator;
        }
        return null;
    }

}
