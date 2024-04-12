package com.archi.todo.controller;

import com.archi.todo.dto.LoginDTO;
import com.archi.todo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user/login")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping()
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
