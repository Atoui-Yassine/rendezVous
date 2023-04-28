package com.example.rendezVous.models.userModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data

@AllArgsConstructor
public class Profile {
    private String coverUrl;
    @Column(length = 3000)
    private String jobTitle ;

    public Profile() {
        this.coverUrl = "";
        this.jobTitle = "";
    }
}
