package com.test.services;

import com.test.dao.UserRepository;
import com.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    * Add a Single User
    * */
    public User addUser(User user){
        return this.userRepository.save(user);
    }

    /*
    *  Find All User
    * */

    public List<User> getAllUser(){
        return this.userRepository.findAll();
    }


    /*
    *  Get User By Address
    * */

    public User findUserByAddress(String address){
        return this.userRepository.findByAddress(address);
    }


    /*
    * Find User By Id
    * */

    public Optional<User> findByUsersId(Integer userId){
        return this.userRepository.findById(userId);
    }

    /*
    *  Delete A Single User
    * */

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    /*
    *  Update A User
    * */

    public User updateUser(User updatedUser) {
        return userRepository.save(updatedUser);
    }


}
