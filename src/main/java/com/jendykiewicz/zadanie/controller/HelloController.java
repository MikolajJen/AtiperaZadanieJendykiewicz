package com.jendykiewicz.zadanie.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }
    @PostConstruct
    public void init(){
        System.out.println(">> HelloController Zaladowany");
    }
}
