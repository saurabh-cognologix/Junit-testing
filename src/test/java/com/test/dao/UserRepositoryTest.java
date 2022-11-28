package com.test.dao;

import com.test.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

//    @BeforeEach
//    public void setup(){
//
//        }

    @Test
    void findByAddress() {
        user = User.builder()
                .userId(2)
                .name("Mona")
                .age(25)
                .address("Varanasi")
                .build();
        userRepository.save(user);

        User userDb = userRepository.findByAddress(user.getAddress());

        assertThat(userDb).isNotNull();
    }

    @Test
    void findByUserId() {
        user = User.builder()
                .userId(1)
                .name("Erix")
                .age(21)
                .address("Ghazipur")
                .build();
        userRepository.save(user);

        // when -  action or the behaviour that we are going test
        User userDB = userRepository.findByUserId(user.getUserId());

        // then - verify the output
        assertThat(userDB).isNotNull();
    }
}