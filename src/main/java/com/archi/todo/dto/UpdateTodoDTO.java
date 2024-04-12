package com.archi.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTodoDTO {
    private Integer index;
    private String content;
    private Boolean checked;
}
