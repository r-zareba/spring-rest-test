package com.example.restfulwebservices.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(path = "/test")
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "/test-object")
    public Hello helloObject() {
        return new Hello("Hello");
    }

    @GetMapping(path = "/test/{name}")
    public Hello helloObjectPath(@PathVariable String name) {
        return new Hello(name);
    }

}
