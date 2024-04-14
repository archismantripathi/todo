package com.archi.todo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewUserDTO {
    @NonNull
    private String fullName;
    @NonNull
    private String username;
    @NonNull
    private String password;
}
