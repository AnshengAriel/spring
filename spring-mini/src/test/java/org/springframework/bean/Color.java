package org.springframework.bean;

import org.springframework.beans.factory.annotation.Autowired;

public enum Color implements FactoryEnum {
    GREEN(1),
    RED(2),
    ;
    Color(Integer type) {
        this.type = type;
    }
    private final Integer type;

    @Autowired
    public static Car car;
    @Autowired
    public static A a;
}
