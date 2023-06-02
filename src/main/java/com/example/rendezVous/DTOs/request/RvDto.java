package com.example.rendezVous.DTOs.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Data
public class RvDto {

    private String title;
    @NotEmpty
    private String description;



    private LocalDateTime dateOfRV;
    @NotEmpty
    private String heure;

}
