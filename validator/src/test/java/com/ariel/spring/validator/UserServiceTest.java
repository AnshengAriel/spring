package com.ariel.spring.validator;

import com.ariel.spring.validator.main.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends CommonTest {

    @Autowired
    private UserService userService;

    @Test
    public void login() {
        String login = userService.login("zs", "sdf");
        System.out.println(login);
    }
}
