package com.example.rendezVous.models.userModel;

import com.example.rendezVous.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Img  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private String url ;
    private String type;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;


}
