package org.springframework.bean;

import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

public interface FactoryEnum {

    String ENUM_SCAN_PACKAGE = "org.springframework.bean";

    Map<String, List<Field>> NEED_AUTOWIRED_FIELD_MAP = loadMapByPackage(ENUM_SCAN_PACKAGE);

    private static Map<String, List<Field>> loadMapByPackage(String path) {
        Map<String, List<Field>> map = new HashMap<>();
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(path, FactoryEnum.class);
        for (Class<?> aClass : classes) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(Autowired.class) != null) {
                    if (map.containsKey(field.getName())) {
                        map.get(field.getName()).add(field);
                    }else {
                        ArrayList<Field> list = new ArrayList<>();
                        list.add(field);
                        map.put(field.getName(), list);
                    }
                }
            }
        }
        return map;
    }

    @Component
    class FactoryEnumApplicationListener implements ApplicationListener<ContextRefreshedEvent>, BeanFactoryAware {

        private BeanFactory beanFactory;

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            // 手动为FactoryEnum子类中被@Autowired标记的属性注入Bean
            NEED_AUTOWIRED_FIELD_MAP.forEach((k, v) -> {
                if (beanFactory.containsBean(k)) {
                    Object bean = beanFactory.getBean(k);
                    for (Field field : v) {
                        field.setAccessible(true);
                        try {
                            field.set(null, bean);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            NEED_AUTOWIRED_FIELD_MAP.clear();
            System.out.println("为枚举类注入Bean成功");
        }
    }
}
