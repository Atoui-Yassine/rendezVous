package com.example.rendezVous.models.userModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Data
@AllArgsConstructor
public class Profile {
    private String coverUrl;
    @Column(length = 3000)
    private String jobTitle ;
    @Column(name = "duration")
    private Integer duration;

    private Double priceHeure;
    public Profile() {
        this.coverUrl = "";
        this.jobTitle = "";

    }
}
