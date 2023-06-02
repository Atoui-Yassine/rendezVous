package com.example.rendezVous.models.rendezVous;

import com.example.rendezVous.models.BaseEntity;
import com.example.rendezVous.models.userModel.UserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous extends BaseEntity {

    @Column(name = "name")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;





    private LocalDateTime dateOfRV;

    private String heure;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @JsonIgnore
    private UserModel client;

    @ManyToOne
    @JoinColumn(name = "id_user_pro")
    @JsonIgnore
    private UserModel userPro;

}
