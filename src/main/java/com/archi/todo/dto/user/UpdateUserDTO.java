package com.archi.todo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserDTO {
    private String fullName;
    private String oldPassword;
    private String newPassword;
}
