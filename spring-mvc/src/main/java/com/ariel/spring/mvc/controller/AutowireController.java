package com.ariel.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class AutowireController {

    @Autowired
    public TestController testController;

    @PostConstruct
    public void init() {
        System.out.println("--------------------------------------------");
        System.out.println(testController.hashCode());
    }

}
