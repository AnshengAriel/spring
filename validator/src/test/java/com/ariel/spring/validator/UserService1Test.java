package com.ariel.spring.validator;

import com.ariel.spring.validator.main.service.UserService1;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService1Test extends CommonTest {

    @Autowired
    private UserService1 userService1;

    @Test
    public void test() {
        System.out.println(userService1 instanceof UserService1);
        System.out.println("userService1.zoneId = " + userService1.zoneId);
        System.out.println("userService1.getZoneId() = " + userService1.getZoneId());
        System.out.println("userService1.getFinalZoneId() = " + userService1.getFinalZoneId());
    }
}
