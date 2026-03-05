package ufck.eventflow.eventflow.controller;

import ufck.eventflow.eventflow.dto.AuthRequest;
import ufck.eventflow.eventflow.entity.User;
import ufck.eventflow.eventflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            return ResponseEntity.badRequest().body("Bruh, it's not you day. Username already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(request.password());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully, bravo!");
    }
}