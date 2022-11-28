package com.test.dao;

import com.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByAddress(String address);
   User findByUserId(Integer userId);
    //User findById(Integer userId);

}
