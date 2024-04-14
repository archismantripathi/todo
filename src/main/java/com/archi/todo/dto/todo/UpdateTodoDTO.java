package com.archi.todo.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTodoDTO {
    private Integer index;
    private String content;
    private Boolean checked;
}
