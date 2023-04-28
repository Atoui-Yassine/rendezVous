package com.example.rendezVous.DTOs.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class JwtResponse {
    private String token,refreshToken;
    private List<String> roles;

}
