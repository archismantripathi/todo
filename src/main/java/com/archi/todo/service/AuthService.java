package com.archi.todo.service;

import com.archi.todo.dto.auth.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String validateToken(String token);

    ResponseEntity login (LoginDTO loginDTO);
}
