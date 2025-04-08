package io.github.wkktoria.game_reviews.controllers;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.wkktoria.game_reviews.dtos.RegisterDto;
import io.github.wkktoria.game_reviews.models.RoleEntity;
import io.github.wkktoria.game_reviews.models.UserEntity;
import io.github.wkktoria.game_reviews.repositories.RoleRepository;
import io.github.wkktoria.game_reviews.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth/")
@Slf4j
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        log.info(String.format("Registering user: %s", registerDto));

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Optional<RoleEntity> role = roleRepository.findByName("USER");

        if (role.isEmpty()) {
            log.info("Creating USER role");
            roleRepository.save(RoleEntity.builder().name("USER").build());
            role = roleRepository.findByName("USER");
        }

        user.setRoles(Collections.singletonList(role.get()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered success");
    }
}
