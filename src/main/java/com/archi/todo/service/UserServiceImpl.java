package com.archi.todo.service;

import com.archi.todo.dto.NewUserDTO;
import com.archi.todo.model.UserData;
import com.archi.todo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public ResponseEntity newUser(NewUserDTO newUserDTO) {
        try {
            if (userRepository.findByUsername(newUserDTO.getUsername()).isPresent())
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username already taken. Please try again");

            if (newUserDTO.getUsername().isEmpty() || newUserDTO.getFullName().isEmpty() || newUserDTO.getPassword().length() < 8)
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error: User data not acceptable");

            return ResponseEntity.status(HttpStatus.CREATED).body("Created " +
                userRepository
                    .save(new UserData(
                            newUserDTO.getUsername(),
                            newUserDTO.getPassword(),
                            newUserDTO.getFullName(),
                            true,
                            null)
                    ).getUsername());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
