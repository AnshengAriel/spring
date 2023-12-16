package org.springframework.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.aop.AdvisedSupport;

import java.lang.reflect.Method;

/**
 * cgli动态代理
 *
 * @author derekyi
 * @date 2020/12/6
 */
public class CglibAopProxy implements AopProxy {

	private final AdvisedSupport advised;

	public CglibAopProxy(AdvisedSupport advised) {
		this.advised = advised;
	}

	@Override
	public Object getProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
		enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
		enhancer.setCallback(this.new DynamicAdvisedInterceptor());
		return enhancer.create();
	}

	/**
	 * 注意此处的MethodInterceptor是cglib中的接口，advised中的MethodInterceptor的AOP联盟中定义的接口，因此定义此类做适配
	 */
	private class DynamicAdvisedInterceptor implements MethodInterceptor {

		@Override
		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			System.out.println("CglibAopProxy$DynamicAdvisedInterceptor#intercept");
			if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
				System.out.println("CglibAopProxy$DynamicAdvisedInterceptor#intercept[" + "代理方法成功了" + "]");
				//代理方法
				return advised.getMethodInterceptor().invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, objects));
			}
			return method.invoke(advised.getTargetSource().getTarget(), objects);
		}

		/*@Override
		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			System.out.println("CglibAopProxy$DynamicAdvisedInterceptor#intercept");
			CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, objects, methodProxy);
			if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
				System.out.println("CglibAopProxy$DynamicAdvisedInterceptor#intercept[" + "代理方法成功了" + "]");
				//代理方法
				return advised.getMethodInterceptor().invoke(methodInvocation);
			}
			return methodInvocation.proceed();
		}*/

	}

}
