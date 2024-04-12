package com.archi.todo.dto;

import lombok.*;

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
