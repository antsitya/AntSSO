package com.ant.sso.AOP;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@Aspect
public class LoginAspect {
    @Pointcut("execution(public * com.ant.sso.Controller..*.*(..))&&@annotation(com.ant.sso.Annotation.Login)")
    public void loginAdvice(){}

    @Around("loginAdvice()")
    public Object loginAround(ProceedingJoinPoint proceedingJoinPoint){
        log.info("in loginAOP...");
        try{
            log.info("method name is {}",proceedingJoinPoint.getTarget());
            Object object=proceedingJoinPoint.proceed();
            return object;
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
