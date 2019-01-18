package com.rests.rests.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWordController {

    @Autowired
    private MessageSource messageSource;

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


    @GetMapping("/i18n")
    public String i18n(){
        return messageSource.getMessage("good.morning.message",null,
                 LocaleContextHolder.getLocale());
    }
}
