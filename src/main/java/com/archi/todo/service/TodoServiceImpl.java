package com.archi.todo.service;

import com.archi.todo.dto.GetTodoListDTO;
import com.archi.todo.dto.NewTodoDTO;
import com.archi.todo.dto.UpdateTodoDTO;
import com.archi.todo.model.Todo;
import com.archi.todo.model.UserData;
import com.archi.todo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity getTodoList(String username) {
        if (username==null) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Unauthorized");
        try {
            Optional<UserData> data = userRepository.findById(username);

            if(data.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");

            System.out.println(new GetTodoListDTO(data.get().getTodoList()).toString());
            return ResponseEntity.accepted().body(new GetTodoListDTO(data.get().getTodoList()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity newTodo(NewTodoDTO newTodoDTO, String username) {
        if (username==null) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Unauthorized");
        try {
            Optional<UserData> data = userRepository.findById(username);

            if(data.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");

            List<Todo> todoList = data.get().getTodoList();

            if(todoList == null)
                todoList = new ArrayList<Todo>();

            Todo todo = new Todo(newTodoDTO.getContent(),newTodoDTO.getChecked());

            todoList.addFirst(todo);

            UserData userData = UserData.builder()
                    .username(username)
                    .password(data.get().getPassword())
                    .fullName(data.get().getFullName())
                    .active(true)
                    .todoList(todoList)
                    .build();

            userRepository.save(userData);

            return ResponseEntity.status(HttpStatus.CREATED).body("Added: New Todo");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity updateTodo(UpdateTodoDTO updateTodoDTO, String username) {
        if (username==null) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Unauthorized");
        try {
            Optional<UserData> data = userRepository.findById(username);

            if(data.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");

            if(data.get().getTodoList()!=null && data.get().getTodoList().size()>updateTodoDTO.getIndex()) {

                List<Todo> todoList = data.get().getTodoList();

                todoList.set(updateTodoDTO.getIndex(), new Todo(updateTodoDTO.getContent(), updateTodoDTO.getChecked()));

                UserData userData = UserData.builder()
                        .username(username)
                        .password(data.get().getPassword())
                        .fullName(data.get().getFullName())
                        .active(true)
                        .todoList(todoList)
                        .build();

                userRepository.save(userData);

                return ResponseEntity.accepted().body("Todo list updated");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry doesn't exists");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity clearTodo(String username) {
        if (username==null) ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Unauthorized");
        try {
            Optional<UserData> data = userRepository.findById(username);

            if(data.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");

            UserData userData = UserData.builder()
                    .username(username)
                    .password(data.get().getPassword())
                    .fullName(data.get().getFullName())
                    .active(true)
                    .todoList(null)
                    .build();

            userRepository.save(userData);

            return ResponseEntity.accepted().body("Cleared all todo");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
