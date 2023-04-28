package com.example.rendezVous.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
    @NotBlank
    private  String email;
    @NotBlank
    private  String firstname;
    @NotBlank
    private  String lastname;

    @NotBlank
    private String password ;

    private Set<String> roles;
}
