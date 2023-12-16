package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * 后置通知
 */
public interface MethodAfterAdvice extends AfterAdvice {

    void after(Method method, Object[] args, Object target);
}
