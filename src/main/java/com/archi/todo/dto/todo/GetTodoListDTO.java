package com.archi.todo.dto.todo;

import com.archi.todo.model.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
public class GetTodoListDTO {
    @JsonProperty("todoList")
    private List<Todo> todoList;
}
