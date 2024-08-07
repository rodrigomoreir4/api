package com.rodrigomoreira.myapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigomoreira.myapp.domain.users.User;
import com.rodrigomoreira.myapp.dtos.UpdateRequest;
import com.rodrigomoreira.myapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);

    }

    @GetMapping("/document") // ex: localhost:8080/users/document?document=12345678910
    public ResponseEntity<?> getUserByDocument(@RequestParam String document) throws Exception{
        User user = userService.findUserByDocument(document);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/addcourse")
    public ResponseEntity<User> addCourse(@RequestBody UpdateRequest update) throws Exception{
        User user = userService.addCourse(update.getDocument(), update.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/removecourse")
    public ResponseEntity<User> removeCourse(@RequestBody UpdateRequest update) throws Exception {
        User user = userService.removeCourse(update.getDocument(), update.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable("id") Long id){
        userService.getUser(id);
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }
 
}
