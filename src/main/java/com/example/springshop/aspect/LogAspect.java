package com.example.springshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private Object object;

    // определяем срез по всем методам бинов из пакета com.example.aop.service
    @Pointcut("execution(* com.example.springshop.service.CartService.*(..))")
    public void logAfterReturning() {
    }

    @AfterReturning(pointcut = "logAfterReturning()", returning = "retVal")
    public void afterReturningAdvice(JoinPoint jp, Object retVal) {
        try {
            System.out.println("User " + jp.getSignature().getName());
            System.out.println(retVal.toString());
        } catch (NullPointerException npe) {
        }
    }
}

