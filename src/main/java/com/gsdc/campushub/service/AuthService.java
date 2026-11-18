package com.gsdc.campushub.service;

import com.gsdc.campushub.dto.AuthRequests;
import com.gsdc.campushub.dto.AuthResponse;
import com.gsdc.campushub.model.Role;
import com.gsdc.campushub.model.User;
import com.gsdc.campushub.repository.UserRepository;
import com.gsdc.campushub.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(AuthRequests.RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail().toLowerCase().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Always enforce STUDENT role on public registration
        user.setRole(Role.STUDENT);
        user.setActive(true);

        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole().name());

        return new AuthResponse(token, savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
    }

    public AuthResponse login(AuthRequests.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!user.isActive()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your account has been deactivated. Contact admin.");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public User getProfile(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
