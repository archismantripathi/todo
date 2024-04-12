package com.archi.todo.controller;

import com.archi.todo.dto.NewTodoDTO;
import com.archi.todo.dto.UpdateTodoDTO;
import com.archi.todo.service.AuthService;
import com.archi.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    AuthService authService;
    @Autowired
    TodoService todoService;

    @GetMapping()
    public ResponseEntity getTodoList(@RequestHeader("Authorization") String token) {
        return todoService.getTodoList(authService.validateToken(token));
    }

    @PostMapping()
    public ResponseEntity newTodo(@RequestBody NewTodoDTO newTodoDTO, @RequestHeader("Authorization")String token){
        return todoService.newTodo(newTodoDTO, authService.validateToken(token));
    }

    @PutMapping()
    public ResponseEntity updateTodo(@RequestBody UpdateTodoDTO updateTodoDTO, @RequestHeader("Authorization")String token) {
        return todoService.updateTodo(updateTodoDTO, authService.validateToken(token));
    }

    @DeleteMapping()
    public ResponseEntity clearTodo(@RequestHeader("Authorization") String token) {
        return todoService.clearTodo(authService.validateToken(token));
    }
}
