package com.archi.todo.controller;

import com.archi.todo.dto.NewUserDTO;
import com.archi.todo.dto.UpdateUserDTO;
import com.archi.todo.service.AuthService;
import com.archi.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @PostMapping()
    public ResponseEntity newUser(@RequestBody NewUserDTO newUserDTO){
        return userService.newUser(newUserDTO);
    }

    @GetMapping()
    public ResponseEntity getUser(@RequestHeader("Authorization") String token) {
        return userService.getUser(authService.validateToken(token));
    }

    @PutMapping()
    public ResponseEntity updateUser(@RequestBody UpdateUserDTO updateUserDTO,@RequestHeader("Authorization")String token) {
        return userService.updateUser(updateUserDTO, authService.validateToken(token));
    }

    @DeleteMapping()
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String token) {
        return userService.deleteUser(authService.validateToken(token));
    }
}