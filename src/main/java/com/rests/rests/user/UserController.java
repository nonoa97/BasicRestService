package com.rests.rests.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoSevice service;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return service.findAll();
    }
    @GetMapping("/users/{id}")
    public Resource<User> getUser(@PathVariable int id){

         User user = service.findOne(id);
         if(user==null)
             throw  new UserNotFoundException("id-"+id);

         //hateoas

        Resource<User> resource = new Resource<User>(user);


            ControllerLinkBuilder linkTo =
                    linkTo(methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-useres"));

        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> newUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

       URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

       return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){

        User user = service.deleteById(id);
        if(user==null)
            throw  new UserNotFoundException("id-"+id);
    }

}
