package com.ariel.spring.validator.main.service;

import com.ariel.spring.validator.aop.AspectHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class UserServiceImpl implements UserService {

    public UserServiceImpl() {
        System.out.println("------------------dev------------------");
    }

    @Override
    @AspectHandler("hello world")
    public String login(String username, String password) {
        return "dev";
    }
}
