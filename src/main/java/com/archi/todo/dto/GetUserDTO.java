package com.archi.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
public class GetUserDTO {
    private String username;
    private String fullName;
}
