package com.example.rendezVous.models.adress;

import com.example.rendezVous.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data

@NoArgsConstructor
public class Address extends BaseEntity {

    private Double lar,lan;
    private String fullAddress;
    private String country,province,city;

    public Address(Double lar, Double lan, String fullAddress, String country, String province, String city) {
        this.lar = lar;
        this.lan = lan;
        this.fullAddress = fullAddress;
        this.country = country;
        this.province = province;
        this.city = city;
    }

}
