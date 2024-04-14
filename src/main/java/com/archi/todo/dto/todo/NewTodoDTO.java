package com.archi.todo.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewTodoDTO {
    @NonNull
    private String content;
    @NonNull
    private Boolean checked;
}
