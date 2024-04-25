package com.archi.todo.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ResUpdateUserDTO {
    private String fullName;
    private Boolean password;
}
