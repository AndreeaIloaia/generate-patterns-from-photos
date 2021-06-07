package com.andreea.app.controller;

import com.andreea.app.AppApplicationTests;
import com.andreea.app.dtos.LoginRequest;
import com.andreea.app.dtos.SignUpRequest;
import com.andreea.app.models.Role;
import com.andreea.app.models.UserEntity;
import com.andreea.app.service.UserServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AppApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImplementation service;

    private ObjectMapper mapper = new ObjectMapper();
    private UserEntity user = new UserEntity();
    private UserEntity admin = new UserEntity();

    @BeforeEach
    public void setup() {
        user.setEmail("test@test.com");
        user.setUsername("test");
        user.setPassword("test");
        user.setAdmin(false);
        admin.setEmail("admin@admin.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setAdmin(true);
    }

    @AfterEach
    public void teardown() {
        service.delete(user);
        service.delete(admin);
    }

    /**
     * Cazuri de testare:
     *  - bad request:   - metoda gresita
     *                   - path gresit
     *                   - parametrii gresiti
     * - se logheaza fara sa completeze nimic
     * - se logheaza numai cu parola ( analog email sau username)
     * - se logheaza inainte de a avea cont (register),
     * - se logheaza cu credentiale gresite
     * - test pentru status code: -200 - ok
     * -400 - bad request
     * - happy flow
     */
    @Test
    public void login() throws Exception {
        setup();

        //metoda gresita
        mockMvc.perform(get("/api/auth/login")).andExpect(status().is4xxClientError()).andReturn();
        mockMvc.perform(post("/api/auth/login*")).andExpect(status().is4xxClientError()).andReturn();
        mockMvc.perform(post("/login")).andExpect(status().is4xxClientError()).andReturn();

        //login fara credentiale
        LoginRequest loginRequest = new LoginRequest();
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        //login doar cu un credential - parola
        loginRequest.setPassword(user.getPassword());
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        //login cu un utilizator inexistent
        loginRequest.setUsernameOrEmail(user.getUsername());
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().is4xxClientError()).andReturn();

        //sign in
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(user.getUsername());
        signUpRequest.setEmail(user.getEmail());
        signUpRequest.setPassword(user.getPassword());
        signUpRequest.setAdmin(user.isAdmin());

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(status().is2xxSuccessful()).andReturn();

        //login cu credentiale gresite
        loginRequest.setPassword("wrongPass");
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        loginRequest.setPassword("test");
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().is2xxSuccessful()).andReturn();

        Map<String,String> map = mapper.readValue(result.getResponse().getContentAsString(), Map.class);
        String accessToken = map.get("accessToken");
        assert (accessToken != null);
        String tokenType = map.get("tokenType");
        assert (tokenType.equals("Bearer"));
        String role = map.get("role");
        assert (role.equals(Role.USER.name()));

        //delete user
//        service.delete(user);

        //signup and login for admin
        signUpRequest.setUsername(admin.getUsername());
        signUpRequest.setEmail(admin.getEmail());
        signUpRequest.setPassword(admin.getPassword());
        signUpRequest.setAdmin(admin.isAdmin());

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(status().is2xxSuccessful()).andReturn();

        loginRequest.setUsernameOrEmail(admin.getUsername());
        loginRequest.setPassword(admin.getPassword());
        result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequest)))
                .andExpect(status().is2xxSuccessful()).andReturn();

        map = mapper.readValue(result.getResponse().getContentAsString(), Map.class);
        String roleAdmin = map.get("role");
        assert (role.equals(Role.USER.name()));

        //delete admin
//        service.delete(admin);
        teardown();
    }


    /**
     * Cazuri de testare:
     *  - bad request:   - metoda gresita
     *                   - path gresit
     * - se inregistreaza fara toate credentialele
     * - exista deja utilizatorul (username sau email)
     * - credentiale gresite - validari minime
     * - happy flow
     */
    @Test
    public void register() throws Exception {
        //metoda gresita
        mockMvc.perform(get("/api/auth/signup")).andExpect(status().is4xxClientError()).andReturn();
        mockMvc.perform(post("/api/auth/signup*")).andExpect(status().is4xxClientError()).andReturn();
        mockMvc.perform(post("/signup")).andExpect(status().is4xxClientError()).andReturn();

        //register fara niciun camp completat
        SignUpRequest signUpRequest = new SignUpRequest();
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        signUpRequest.setUsername(user.getUsername());
        signUpRequest.setEmail(user.getEmail());

        //register fara un camp completat - password
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        signUpRequest.setPassword(user.getPassword());
        signUpRequest.setAdmin(user.isAdmin());

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(status().is2xxSuccessful()).andReturn();

        //daca exista deja utilizator cu acest username
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        //daca exista deja utilizator cu acest email
        signUpRequest.setUsername("test2");
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest()).andReturn();

        service.delete(user);
    }

}
