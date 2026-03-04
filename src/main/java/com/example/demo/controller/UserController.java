package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public UserResponse create(@RequestBody UserRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public UserResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable Long id) {
        service.delete(id);
        return Map.of("success", true);
    }
}
