package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя должно состоять из 3 и более букв")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен быть минимум из 8 символов")
    private String password;
}