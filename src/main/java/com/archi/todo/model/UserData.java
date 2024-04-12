package com.archi.todo.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document("user")
public class UserData {
    @Id
    private String id;
    @Indexed
    private String username;
    private String password;
    private String fullName;
    private List<Todo> todoList;
}
