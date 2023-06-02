package com.example.rendezVous.models.address;

import com.example.rendezVous.DTOs.views.View;
import com.example.rendezVous.models.BaseEntity;
<<<<<<< Updated upstream:src/main/java/com/example/rendezVous/models/adress/Address.java
import com.example.rendezVous.models.userModel.UserModel;
import com.fasterxml.jackson.annotation.JsonView;
=======
>>>>>>> Stashed changes:src/main/java/com/example/rendezVous/models/address/Address.java
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data

@NoArgsConstructor
public class Address extends BaseEntity {

    private Double lat, lon;
    @JsonView(View.base.class)

    private String fullAddress;
    @JsonView(View.base.class)

    private String country,province,city;
    @OneToOne
    private UserModel userModel;
    public Address(Double lat, Double lon, String fullAddress, String country, String province, String city) {
        this.lat = lat;
        this.lon = lon;
        this.fullAddress = fullAddress;
        this.country = country;
        this.province = province;
        this.city = city;
    }

}
