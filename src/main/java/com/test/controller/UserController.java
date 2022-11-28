package com.test.controller;

import com.test.model.User;
import com.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/getUsers")
    public List<User> findAllUsers() {
        return userService.getAllUser();
    }


    // Get Handler --> get user by id
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        User user =  userService.findByUsersId(id);
        return  ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(user);

    }


    @GetMapping("/getUserByAddress/{address}")
    public User findUserByAddress(@PathVariable String address) {
        return userService.findUserByAddress(address);
   }

    @DeleteMapping(value="/remove/{userId}")
    public void removeUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
    }
}
