package com.archi.todo.service;

import com.archi.todo.dto.NewTodoDTO;
import com.archi.todo.dto.UpdateTodoDTO;
import org.springframework.http.ResponseEntity;

public interface TodoService {
    ResponseEntity getTodoList(String s);

    ResponseEntity newTodo(NewTodoDTO newTodoDTO);

    ResponseEntity updateTodo(UpdateTodoDTO updateTodoDTO, String s);

    ResponseEntity deleteTodo(String s);
}