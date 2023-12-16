package org.springframework.context.support;

import org.springframework.beans.BeansException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * xml文件的应用上下文
 * ---对象很复杂，但json很简单
 *
 * @author derekyi
 * @date 2020/11/28
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	private String[] configLocations;
	private Field field;

	/**
	 * 从xml文件加载BeanDefinition，并且自动刷新上下文<br>
	 * e.g.
	 * <ul>
	 * <li>{"configLocation":"classpath:spring.xml"} -> {}
	 * </ul>
	 * @param configLocation xml配置文件
	 * @throws BeansException 应用上下文创建失败
	 */
	public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
		this(new String[]{configLocation});
	}

	/**
	 * 从xml文件加载BeanDefinition，并且自动刷新上下文
	 *
	 * @param configLocations xml配置文件
	 * @throws BeansException 应用上下文创建失败
	 */
	public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
		this.configLocations = configLocations;
		refresh();
	}

	@Override
	protected String[] getConfigLocations() {
		return this.configLocations;
	}
/*
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		addField(this, builder);
		return builder.toString();
	}

	public StringBuilder addField(Object o, StringBuilder builder) {
		// 获取对象的所有属性和变量，包含所有的基类
		for (Method method : this.getClass().getMethods()) {
			if (method.getName().startsWith("get")) {
				String firstStr = String.valueOf(method.getName().charAt(3)).toLowerCase();
				builder.append("\"")
						.append(firstStr)
						.append(method.getName().substring(4))
						.append("\":\"");
				try {
					builder.append(addField(method.invoke(this), builder));
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return builder.deleteCharAt(builder.length()-1)
				.append("}");
	}*/
}
