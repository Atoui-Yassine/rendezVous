package com.example.rendezVous.DTOs.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
