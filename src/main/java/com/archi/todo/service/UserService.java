package com.archi.todo.service;

import com.archi.todo.dto.NewUserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity newUser(NewUserDTO newUserDTO);
}
