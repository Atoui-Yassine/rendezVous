package com.example.rendezVous.DTOs.request;

import com.example.rendezVous.models.rendezVous.Status;
import com.example.rendezVous.utils.EnumTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusRVConvert {
    @JsonDeserialize(using = EnumTypeDeserializer.class)
    private Status status;
}
