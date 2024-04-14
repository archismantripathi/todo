package com.archi.todo.service;

import com.archi.todo.dto.auth.LoginDTO;
import com.archi.todo.model.UserData;
import com.archi.todo.repository.UserRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    private String secret = "secret";

    private String randomStringGenerator(int targetStringLength) {
        int leftLimit = 48;     // numeral '0'
        int rightLimit = 122;   // letter 'z'

        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private String generateToken(String username, String secret, String randomString) {
        String token = Hashing.sha256()
                .hashString(username+secret+randomString, StandardCharsets.UTF_8)
                .toString();
        return token + randomString + username;
    }

    @Override
    public String validateToken(String token) {
        if(token!=null && token.length() > 80) {
            String username = token.substring(80);
            String randomString = token.substring(64, 80);
            if (token.equals(generateToken(username, this.secret, randomString)))
                return username;
        }
        return null;
    }

    @Override
    public ResponseEntity login(LoginDTO loginDTO) {
        try{
            Optional<UserData> userData = userRepository.findById(loginDTO.getUsername());

            if (userData.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>(){{put("message", "Error: User not found");}});

            String password = Hashing.sha256()
                    .hashString(loginDTO.getPassword(), StandardCharsets.UTF_8)
                    .toString();
            if(password.equals(userData.get().getPassword())) {
                String randomString = randomStringGenerator(16);

                return ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .header("ok","true")
                        .body(new HashMap<String, String>(){{put("message", generateToken(loginDTO.getUsername(), secret, randomString));}});
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new HashMap<String, String>(){{put("message", "Error: Password mismatch");}});
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
