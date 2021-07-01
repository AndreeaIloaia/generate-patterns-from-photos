package com.andreea.app.controller;

import com.andreea.app.auth.*;
import com.andreea.app.dtos.ApiResponse;
import com.andreea.app.dtos.LoginRequest;
import com.andreea.app.dtos.SignUpRequest;
import com.andreea.app.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller dedicat pentru login si register
 */
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    public AuthController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    /**
     * Metoda login folosing username/email si parola
     * Nu e nevoie de explicitarea rolului in request
     * @param loginRequest - LoginRequest
     * @return ResponseEntity
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
//            Role role = userServiceImplementation.checkIfAdmin(loginRequest.getUsernameOrEmail()) ? Role.ADMIN : Role.USER;
//            return new ResponseEntity<>(new JwtAuthenticationResponse(jwt, role), HttpStatus.OK);
            return new ResponseEntity<>(new JwtAuthenticationResponse(jwt), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Bad credentials. Try again!"),
                    HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Sign in first!"),
                    HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Metoda register pentru a inregistra un nou utilizator, dandu-se username-ul, email-ul, parola si daca este admin
     * Nu e nevoie de explicitarea rolului in request
     * @param signUpRequest - SignUpRequest
     * @return ResponseEntity
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(signUpRequest.getUsername() == null || signUpRequest.getEmail() == null || signUpRequest.getPassword() == null){
            return new ResponseEntity<>(new ApiResponse(false, "Inputs must not be null!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userServiceImplementation.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userServiceImplementation.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        //create user's account
//        UserEntity userEntity = new UserEntity(signUpRequest.getUsername(), signUpRequest.getPassword());
//
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//        userEntity.setAdmin(signUpRequest.getIsAdmin());
//        userEntity.setEmail(signUpRequest.getEmail());
//
//        UserEntity result = userServiceImplementation.save(userEntity);
        boolean isSaved = userServiceImplementation.saveUser(signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getIsAdmin(), signUpRequest.getEmail());

        if (isSaved) {
            return new ResponseEntity<>(new ApiResponse(true, "User registered successfully"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, "User registered not successfully. Try again!"),
                HttpStatus.NOT_FOUND);
    }
}
