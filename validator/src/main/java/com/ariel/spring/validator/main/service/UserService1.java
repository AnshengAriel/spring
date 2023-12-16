package com.ariel.spring.validator.main.service;

import com.ariel.spring.validator.aop.AspectHandler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZoneId;

@Service
public class UserService1 {

    // 成员变量:
    public ZoneId zoneId = ZoneId.systemDefault();

    // 成员变量:
    public ZoneId zoneId1;

    // 成员变量:
    public ZoneId zoneId2;

    // 构造方法:
    public UserService1() {
        System.out.println("UserService(): init...");
        System.out.println("UserService(): zoneId = " + this.zoneId);
    }

    // public方法:
    @AspectHandler("asd")
    public ZoneId getZoneId() {
        return zoneId;
    }

    // public final方法:
    public final ZoneId getFinalZoneId() {
        return zoneId;
    }

}
