package com.andreea.app.controller;

import com.andreea.app.dtos.ApiResponse;
import com.andreea.app.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api")
public class UserController {

    private final UserServiceImplementation serviceImplementation;

    @Autowired
    public UserController(UserServiceImplementation serviceImplementation) {
        this.serviceImplementation = serviceImplementation;
    }

    /**
     * test function
      * @return ResponseEntity
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> test() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return new ResponseEntity<>(new ApiResponse(true, "Hello darkness, my old friend!"),
                HttpStatus.OK);
    }

}
