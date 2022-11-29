package com.test.services;


import com.test.dao.UserRepository;
import com.test.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp(){

//        Optional<User> user = Optional.of(new User(1,"Ranno",25,"Basti"));
//        Mockito.when(repository.findByAddress("Basti")).thenReturn(user);
        user = User.builder()
                .userId(101)
                .name("Sonu")
                .age(24)
                .address("Banatas")
                .build();
    }


    @Test
    void addUser() {
//        User user = new User(1,"Ranno",33,"Basti");
//        when(repository.save(user)).thenReturn(user);
//        assertEquals(user, userService.addUser(user));
        given(userRepository.findById(user.getUserId()))
                .willReturn(Optional.empty());
        given(userRepository.save(user)).willReturn(user);

        User savedUser = userService.addUser(user);
        assertThat(savedUser).isNotNull();


    }

    @Test
    void getAllUser() {
        when(userRepository.findAll()).thenReturn(Stream
                .of(new User(376, "Sonu", 31,"New York"), new User(102, "Monu", 35,"California"))
                .collect(Collectors.toList()));
        assertEquals(2, userService.getAllUser().size());
    }

    @DisplayName("JUnit test for findUserByAddress method")
    @Test
    void findUserByAddress() {
        //given
        given(userRepository.findByAddress("Basti")).willReturn((user));
        // when
        User savedUser = userService.findUserByAddress(user.getAddress());
        // then
        assertThat(savedUser).isNotNull();

    }

    @Test
    void deleteUser() {
        // given - precondition or setup
        Integer userId = 101;
        willDoNothing().given(userRepository).deleteById(userId);
        // when -  action or the behaviour that we are going test
        userService.deleteUser(userId);
        // then - verify the output
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void findByUsersId() {

        //given
        given(userRepository.findById(101)).willReturn(Optional.of(user));
        // when
        User savedUser = userService.findByUsersId(user.getUserId()).get();
        // then
        assertThat(savedUser).isNotNull();
    }

    @Test
    void updateUser() {
        // given - precondition or setup
        given(userRepository.save(user)).willReturn(user);
        user.setName("Mona");
        user.setAddress("Varanasi");
        user.setAge(30);

        // when -  action or the behaviour that we are going test
        User updatedUser = userService.updateUser(user);

        // then - verify the output
        assertThat(updatedUser.getName()).isEqualTo("Mona");
        assertThat(updatedUser.getAddress()).isEqualTo("Varanasi");
        assertThat(updatedUser.getAge()).isEqualTo(30);

    }
}