package com.archi.todo.controller;

import com.archi.todo.dto.NewUserDTO;
import com.archi.todo.dto.UpdateUserDTO;
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

    @PostMapping()
    public ResponseEntity newUser(@RequestBody NewUserDTO newUserDTO){
        return userService.newUser(newUserDTO);
    }

    @GetMapping()
    public String getUser() {
        return "this is get";
    }

    @PutMapping()
    public String updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        return "this is put";
    }

    @DeleteMapping()
    public String deleteUser() {
        return "this is delete";
    }
}