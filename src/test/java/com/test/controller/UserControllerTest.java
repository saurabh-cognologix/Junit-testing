package com.test.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import net.javaguides.springboot.model.Employee;
//import net.javaguides.springboot.service.EmployeeService;

import com.test.model.User;
import com.test.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    //Injecting MockMvc class to make HTTP request using perform() method
    @Autowired
    private MockMvc mockMvc;


    //For reading and writing java JSON
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserController userController;






    @Test
    void saveUser() throws Exception  {
        //given -precondition or setup
        User user = User.builder()
                .userId(101)
                .name("Mona")
                .age(45)
                .address("Chennai")
                .build();

        given(userService.addUser(any(User.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        //when - action or behaviour we are going to test
        ResultActions response = mockMvc.perform(post("/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));


        //then - verify
        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId",
                        CoreMatchers.is(user.getUserId())))
                .andExpect(jsonPath("$.name",
                        CoreMatchers.is(user.getName())))
                .andExpect(jsonPath("$.age",
                        CoreMatchers.is(user.getAge())))
                .andExpect(jsonPath("$.address",
                        CoreMatchers.is(user.getAddress())));

    }

    @Test
    void findAllUsers() throws Exception {
        // given - precondition or setup
        List<User> listOfUser = new ArrayList<>();
        listOfUser.add(User.builder().userId(101).name("Golu").age(28).address("Var").build());
        listOfUser.add(User.builder().userId(102).name("Monu").age(30).address("Bas").build());
        given(userService.getAllUser()).willReturn(listOfUser);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/user/getUsers"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfUser.size())));

    }

   @Test
   void getUser() throws Exception {
            //given
                Integer uId = 101;
                User user = User.builder()
                        .userId(101)
                        .name("Mona")
                        .age(45)
                        .address("Chennai")
                        .build();
                given(userService.findByUsersId(uId)).willReturn((Optional.of(user)));

                // when -  action or the behaviour that we are going test
                ResultActions response = mockMvc.perform(get("/user/get/{id}", uId));

                // then - verify the output
                response.andDo(print())
                       // .andExpect(jsonPath("$.userId", is(user.getUserId())))
                        .andExpect(jsonPath("$.name", is(user.getName())))
                        .andExpect(jsonPath("$.age", is(user.getAge())))
                        .andExpect(jsonPath("$.address",is(user.getAddress())));


        }

    //negative scenario - valid user id
    //Junit test for Get employee By Id Rest Api
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
//        Integer uId = 201;
//        User user = User.builder()
//                //.userId(101)
//                .name("Mona")
//                .age(45)
//                .address("Chennai")
//                .build();
//
//        given(userService.findByUsersId(uId)).willReturn(Optional.empty());
//
//        // when -  action or the behaviour that we are going test
//        ResultActions response = mockMvc.perform(get("/user/get/{id}", uId));
//
//        // then - verify the output
//        response.andExpect(status().isNotFound())
//                .andDo(print());

    }

    @Test
    void findUserByAddress() {
    }

    @Test
    void removeUser() throws Exception {
        // given - precondition or setup
        Integer userId = 101;
        willDoNothing().given(userService).deleteUser(userId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/user/remove/{userId}", userId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void updateUser() throws Exception {
        Integer userId = 101;
        User savedUser = User.builder()
                .name("Monu")
                .age(24)
                .address("Pune")
                .build();

        User updatedUser = User.builder()
                .name("Sonu")
                .age(25)
                .address("Karwar")
                .build();
        given(userService.findByUsersId(userId)).willReturn(Optional.of(savedUser));
        given(userService.updateUser(any(User.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.age", is(updatedUser.getAge())))
                .andExpect(jsonPath("$.address",is(updatedUser.getAddress())));
    }
}