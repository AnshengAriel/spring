package com.ariel.spring.validator.domain;

import com.ariel.spring.validator.init.Match;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserVo {

    @Match(regexp = "^[1-5]+", message = "id cannot be null")
    private Integer id = 1;

    @Match(regexp = "^a.+", message = "name is invalid")
    private String name;

}
