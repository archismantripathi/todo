package com.archi.todo.service;

import com.archi.todo.dto.LoginDTO;
import com.archi.todo.model.UserData;
import com.archi.todo.repository.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private String secret = "secret";

    private String generateToken(String username,String secret) {
        String token = Hashing.sha256()
                .hashString(username+secret, StandardCharsets.UTF_8)
                .toString();
        return token + username;
    }

    @Override
    public String validateToken(String token) {
        String username = token.substring(64);
        if(token.equals(generateToken(username, this.secret)))
            return username;
        return null;
    }

    @Override
    public ResponseEntity login(LoginDTO loginDTO) {
        try{
            Optional<UserData> userData = userRepository.findById(loginDTO.getUsername());

            if (userData.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found");

            String password = Hashing.sha256()
                    .hashString(loginDTO.getPassword(), StandardCharsets.UTF_8)
                    .toString();
            if(password.equals(userData.get().getPassword())) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(generateToken(loginDTO.getUsername(),this.secret));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Password mismatch");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
