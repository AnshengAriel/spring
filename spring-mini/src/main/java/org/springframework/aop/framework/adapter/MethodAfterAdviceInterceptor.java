package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodAfterAdvice;

public class MethodAfterAdviceInterceptor implements MethodInterceptor {

    private MethodAfterAdvice methodAfterAdvice;

    public MethodAfterAdviceInterceptor() {

    }

    public MethodAfterAdviceInterceptor(MethodAfterAdvice methodAfterAdvice) {
        this.methodAfterAdvice = methodAfterAdvice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("MethodAfterAdviceInterceptor#invoke");
        Object proceed = methodInvocation.proceed();
        methodAfterAdvice.after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return proceed;
    }
}
