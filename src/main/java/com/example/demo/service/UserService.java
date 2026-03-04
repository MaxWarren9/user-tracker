package com.example.demo.service;

import com.example.demo.enums.UserStatus;
import com.example.demo.exception.UserException;
import lombok.RequiredArgsConstructor;
import com.example.demo.model.User;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UserResponse create(UserRequest request) {

        String hash = encoder.encode(request.getPassword());

        User user = User.builder()
                        .username(request.getUsername())
                        .passwordHash(hash)
                        .status(UserStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build();

        User saved = repository.save(user);

        return new UserResponse(saved.getId(), saved.getUsername());
    }

    public UserResponse getById(Long id) {
        User user = repository.findById(id)
                              .orElseThrow(() -> new UserException("User not found"));

        return new UserResponse(user.getId(), user.getUsername());
    }

    public List<UserResponse> getAll() {
        return repository.findAll()
                         .stream()
                         .map(u -> new UserResponse(u.getId(), u.getUsername()))
                         .toList();
    }

    public void delete(Long id) {
        repository.softDelete(id);
    }
}