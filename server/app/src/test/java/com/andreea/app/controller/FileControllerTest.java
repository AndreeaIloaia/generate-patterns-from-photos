package com.andreea.app.controller;

import com.andreea.app.AppApplicationTests;
import com.andreea.app.auth.UserPrincipal;
import com.andreea.app.dtos.FileUploadResponse;
import com.andreea.app.dtos.LoginRequest;
import com.andreea.app.dtos.SignUpRequest;
import com.andreea.app.models.FileEntity;
import com.andreea.app.models.UserEntity;
import com.andreea.app.service.FileServiceImplementation;
import com.andreea.app.service.GarmentServiceImplementation;
import com.andreea.app.service.UserServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test FileController.
 */
public class FileControllerTest extends AppApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileServiceImplementation fileService;
    @Autowired
    private GarmentServiceImplementation garmentService;
    @Autowired
    private UserServiceImplementation userService;

    private ObjectMapper mapper = new ObjectMapper();
    private FileEntity fileEntity = new FileEntity();
    private UserEntity testUser = new UserEntity();
    private String token;

    @BeforeEach
    public void setup() throws Exception {
        testUser.setUsername("test");
        testUser.setPassword("test");
        testUser.setEmail("test@gmail.com");
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(testUser.getUsername());
        signUpRequest.setEmail(testUser.getEmail());
        signUpRequest.setPassword(testUser.getPassword());
        signUpRequest.setAdmin(testUser.isAdmin());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail(testUser.getUsername());
        loginRequest.setPassword(testUser.getPassword());
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(signUpRequest)));
        var result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest))).andReturn();
        Map map = mapper.readValue(result.getResponse().getContentAsString(), Map.class);
        token = (String) map.get("accessToken");
    }

    @After
    public void teardown() {
        userService.delete(testUser);
    }

    /**
     * Cazuri de testare:
     *  - bad request:   - metoda gresita
     *                   - path gresit
     *                   - parametrii gresiti
     * - 400 - bad request
     * - happy flow
     */
    @Test
    public void upload() throws Exception {
        setup();

        //metoda gresita
        mockMvc.perform(get("/api/files/upload")).andExpect(status().is4xxClientError()).andReturn();
        mockMvc.perform(post("/api/files/*")).andExpect(status().is4xxClientError()).andReturn();
//        mockMvc.perform(post("/login")).andExpect(status().is4xxClientError()).andReturn();

        MockMultipartFile file = new MockMultipartFile("file","hello.txt",
                MediaType.TEXT_PLAIN_VALUE,"Hello, World!".getBytes());
        String type = "rochie";

        mockMvc.perform(multipart("/api/files/upload/".concat(type)).file(file)).andExpect(status().is4xxClientError());
        String auth = "Authorization";
        String bearer = "Bearer " + token;
        mockMvc.perform(multipart("/api/files/upload/".concat(type)).file(file).header(HttpHeaders.AUTHORIZATION, bearer)).andExpect(status().is2xxSuccessful());

        teardown();
    }
}
