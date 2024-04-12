package com.archi.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDataDTO {
    private String fullName;
    private String username;
    private String password;
}
