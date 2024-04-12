package com.archi.todo.service;

import com.archi.todo.dto.NewTodoDTO;
import com.archi.todo.dto.UpdateTodoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService{
    @Override
    public ResponseEntity getTodoList(String s) {
        return null;
    }

    @Override
    public ResponseEntity newTodo(NewTodoDTO newTodoDTO) {
        return null;
    }

    @Override
    public ResponseEntity updateTodo(UpdateTodoDTO updateTodoDTO, String s) {
        return null;
    }

    @Override
    public ResponseEntity deleteTodo(String s) {
        return null;
    }
}
