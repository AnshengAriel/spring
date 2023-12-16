package com.ariel.spring.validator.main.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class UserServiceTestImpl implements UserService {

    public UserServiceTestImpl() {
        System.out.println("------------------test------------------");
    }

    @Override
    public String login(String username, String password) {
        return "test";
    }
}
