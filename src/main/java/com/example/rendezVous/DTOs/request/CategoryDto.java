package com.example.rendezVous.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @NotNull(message = "name is required")
    @Size(message = "name must be equal to or lower than 100", min = 1, max = 100)
    private String name;
}
