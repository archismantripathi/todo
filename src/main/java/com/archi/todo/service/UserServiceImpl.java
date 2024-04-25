package com.archi.todo.service;

import com.archi.todo.dto.user.GetUserDTO;
import com.archi.todo.dto.user.NewUserDTO;
import com.archi.todo.dto.user.ResUpdateUserDTO;
import com.archi.todo.dto.user.UpdateUserDTO;
import com.archi.todo.model.UserData;
import com.archi.todo.repository.UserRepository;
import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity newUser(NewUserDTO newUserDTO) {
        try {
            if (userRepository.findById(newUserDTO.getUsername()).isPresent())
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new HashMap<String, String>(){{put("message", "Error: Username already taken. Please try again");}});

            if (newUserDTO.getUsername().isEmpty() || newUserDTO.getFullName().isEmpty() || newUserDTO.getPassword().length() < 8)
                return ResponseEntity
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new HashMap<String, String>(){{put("message", "Error: User data not acceptable");}});

            String password = Hashing.sha256()
                    .hashString(newUserDTO.getPassword(), StandardCharsets.UTF_8)
                    .toString();

            userRepository
                    .save(new UserData(
                            newUserDTO.getUsername(),
                            password,
                            newUserDTO.getFullName(),
                            true,
                            null)
                    );

            return ResponseEntity
                    .accepted()
                    .body(new HashMap<String, String>(){{put("message", "Created: " + newUserDTO.getUsername());}});

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity getUser(String username) {
        if (username==null)
            ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new HashMap<String, String>(){{put("message", "Error: Unauthorized");}});

        Optional<UserData> data = userRepository.findById(username);

        if(data.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new HashMap<String, String>(){{put("message", "Error: User not found");}});

        GetUserDTO getUserDTO = GetUserDTO.builder()
                .username(username)
                .fullName(data.get().getFullName())
                .build();

        return ResponseEntity.accepted().body(new HashMap<String, GetUserDTO>(){{put("message", getUserDTO);}});
    }

    @Override
    public ResponseEntity updateUser(UpdateUserDTO updateUserDTO, String username) {
        if (username==null) ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new HashMap<String, String>(){{put("message", "Error: Unauthorized");}});

        try {
            Optional<UserData> currentData = userRepository.findById(username);
            if (currentData.isEmpty())
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new HashMap<String, String>(){{put("message", "Error: User not found");}});

            ResUpdateUserDTO res = ResUpdateUserDTO.builder().fullName(false).password(false).build();
            String oldPassword = Hashing.sha256()
                    .hashString(updateUserDTO.getOldPassword(), StandardCharsets.UTF_8)
                    .toString();

            if (Objects.equals(currentData.get().getPassword(), oldPassword)) {
                UserData userData = UserData.builder()
                        .username(username)
                        .password(currentData.get().getPassword())
                        .fullName(currentData.get().getFullName())
                        .active(true)
                        .todoList(currentData.get().getTodoList())
                        .build();
                if (updateUserDTO.getFullName() != null && !updateUserDTO.getFullName().isEmpty()) {
                    userData.setFullName(updateUserDTO.getFullName());
                    res.setFullName(true);
                }

                if (updateUserDTO.getOldPassword() != null && updateUserDTO.getNewPassword() != null && updateUserDTO.getNewPassword().length() > 7) {
                    String password = Hashing.sha256()
                            .hashString(updateUserDTO.getNewPassword(), StandardCharsets.UTF_8)
                            .toString();
                    userData.setPassword(password);
                    res.setPassword(true);
                }
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new HashMap<String, ResUpdateUserDTO>(){{put("message", res);}});

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity deleteUser(String username) {
        if (username==null) ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new HashMap<String, String>(){{put("message", "Error: Unauthorized");}});

        try {
            if(userRepository.findById(username).isEmpty())
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new HashMap<String, String>(){{put("message", "Error: User not found");}});

            userRepository.deleteById(username);
            return ResponseEntity
                    .accepted()
                    .body(new HashMap<String, String>(){{put("message", "Removed: "+username);}});

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
