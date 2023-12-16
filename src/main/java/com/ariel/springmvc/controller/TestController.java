package com.ariel.springmvc.controller;

import com.ariel.springmvc.util.ObserverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/springmvc/test")
public class TestController {

    @Autowired
    private DispatcherServlet dispatcherServlet;

    @PostConstruct
    public void init() {
//        ObserverUtil.observe(dispatcherServlet);
//        ObserverUtil.append("TestController#init");
    }

    @PostMapping("/hello")
    Object hello(@RequestBody Map<String, Object> params) {
        System.out.println(params);
        return params;
    }
}
