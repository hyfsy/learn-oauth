package com.hyf.oauth2.controller;

import com.hyf.oauth2.model.Person;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author baB_hyf
 * @date 2021/03/19
 */
@RestController
@RequestMapping("visit")
public class VisitController {

    @CrossOrigin
    @RequestMapping("person")
    public Person person() {
        return new Person("张三", 18);
    }
}
