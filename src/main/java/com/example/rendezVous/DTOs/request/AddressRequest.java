package com.example.rendezVous.DTOs.request;

import com.example.rendezVous.models.adress.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotNull
    @DecimalMin(value = "-90.00")
    @DecimalMax(value = "90.00")
    private Double lar, lon;
    private String fullAddress;
    private String country,province,city;

}
