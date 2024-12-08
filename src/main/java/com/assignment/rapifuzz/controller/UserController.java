package com.assignment.rapifuzz.controller;

import com.assignment.rapifuzz.dto.request.LoginRequest;
import com.assignment.rapifuzz.dto.response.LoginResponseDTO;
import com.assignment.rapifuzz.dto.response.ResponseMessageDTO;
import com.assignment.rapifuzz.entity.User;
import com.assignment.rapifuzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDTO(e.getMessage(), "BAD_REQUEST"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessageDTO(e.getMessage(), "UNAUTHORIZED"));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.processForgotPassword(email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
