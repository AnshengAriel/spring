package com.ariel.spring.validator.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AspectHandlerConfig {

    @Around("@annotation(aspectHandler)")
    public Object test(ProceedingJoinPoint joinPoint, AspectHandler aspectHandler) throws Throwable {
        System.out.println(aspectHandler.value());
        Object proceed = joinPoint.proceed();
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(args));
        return proceed;
    }

}
