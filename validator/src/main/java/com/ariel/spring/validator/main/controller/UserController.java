package com.ariel.spring.validator.main.controller;

import com.ariel.spring.validator.domain.UserVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/ariel/validator/user")
public class UserController {

    @Value("classpath:static/a.txt")
//    @Value("file:src/main/resources/static/a.txt")
    private Resource resource;

    @PostConstruct
    public void init() {
        try (InputStream in = resource.getInputStream()) {
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            System.out.write(bytes);
            System.out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody @Validated UserVo vo) {
        return "success";
    }

}
