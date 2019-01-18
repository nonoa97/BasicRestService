package com.rests.rests.helloworld;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWordController {

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World";
    }



    @GetMapping("/bean")
    public HelloWorldBean helloWorldBean(){

        return new HelloWorldBean("Helloka nyalóka");
    }


    @GetMapping("/bean/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){

        return new HelloWorldBean(String.format("Nyali, %s", name));
    }
}
