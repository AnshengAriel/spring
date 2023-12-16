package org.springframework.aop;

import java.lang.reflect.Method;

public class WorldServiceAfterAdvice implements MethodAfterAdvice {

    @Override
    public void after(Method method, Object[] args, Object target) {
        System.out.println("WorldServiceAfterAdvice#after");
    }
}
