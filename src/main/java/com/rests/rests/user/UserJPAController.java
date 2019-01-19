package com.rests.rests.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController()
public class UserJPAController {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> getAllUsers(){

        return userRepository.findAll();
    }


    @GetMapping("/jpa/users/{id}")
    public Resource<User> getUser(@PathVariable int id){

         Optional<User> user = userRepository.findById(id);

         if(!user.isPresent())
             throw  new UserNotFoundException("id-"+id);

         //hateoas

        Resource<User> resource = new Resource<User>(user.get());


            ControllerLinkBuilder linkTo =
                    linkTo(methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-useres"));

        return resource;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> newUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);

       URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

       return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){

       userRepository.deleteById(id);

    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> getPostsFromUser(@PathVariable int id ){

             Optional<User> userOptional =   userRepository.findById(id);
            if(!userOptional.isPresent()){
                throw new UserNotFoundException("id-"+id);
            }

        return userOptional.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post){

        Optional<User> userOptional =   userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("id-"+id);
        }
        User user = userOptional.get();

        post.setUser(user);
        postRepository.save(post);



        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
