package com.archi.todo.service;

import com.archi.todo.dto.user.NewUserDTO;
import com.archi.todo.dto.user.UpdateUserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity newUser(NewUserDTO newUserDTO);

    ResponseEntity updateUser(UpdateUserDTO updateUserDTO, String username);

    ResponseEntity deleteUser(String username);

    ResponseEntity getUser(String s);
}
