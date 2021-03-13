package com.andreea.app.controller;

import com.andreea.app.models.UserEntity;
import com.andreea.app.service.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    public MockMvc mvc;

//    @Autowired
//    UserServiceImplementation userServiceImplementation;
//
//    public AuthControllerTest(UserServiceImplementation userServiceImplementation) {
//        this.userServiceImplementation = userServiceImplementation;
//    }
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//    private UserEntity user = new UserEntity();
//    private UserEntity admin = new UserEntity();

    @BeforeEach
    void setUp() {
//        user.setEmail("test@test.com");
//        user.setUsername("test");
//        user.setPassword("test");
//        user.setAdmin(false);
//        admin.setEmail("admin@admin.com");
//        admin.setUsername("admin");
//        admin.setPassword("admin");
//        admin.setAdmin(true);
    }

    @AfterEach
    void tearDown() {
//        userServiceImplementation.delete(user);
//        userServiceImplementation.delete(admin);
    }

    /**
     * Cazuri de testare:
     * - se logheaza inainte de a avea cont (register),
     * - se logheaza fara sa completeze nimic
     * - se logheaza numai cu -parola
     * -email/username
     * - se logheaza cu credentiale gresite
     * - se logheaza fara field-ul isAdmin
     * - test pentru status code: -200 - ok
     * -400 - bad request
     * - bad request:  - metoda gresita
     * - path gresit
     * - parametrii gresiti
     * - happy flow
     */
    @Test
    void authenticateUser() throws Exception {
//        setUp();
//
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsernameOrEmail(user.getUsername());
//        loginRequest.setPassword(user.getPassword());


//        MvcResult result = mvc.perform(post("/api/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(loginRequest)))
//                .andExpect(status().is4xxClientError()).andReturn();


    }

    @Test
    void registerUser() {
    }
}