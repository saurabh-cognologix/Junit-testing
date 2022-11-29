package com.test.controller;

import com.test.model.User;
import com.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        return userService.findByUsersId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }


    @GetMapping("/getUserByAddress/{address}")
    public User findUserByAddress(@PathVariable String address) {
        return userService.findUserByAddress(address);
   }

    @DeleteMapping(value="/remove/{userId}")
    public void removeUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer userId,
                                                   @RequestBody User user){
        return userService.findByUsersId(userId)
                .map(savedUser -> {
                    savedUser.setName(user.getName());
                    savedUser.setAge(user.getAge());
                    savedUser.setAddress(user.getAddress());
                    User updatedUser = userService.updateUser(savedUser);
                    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
