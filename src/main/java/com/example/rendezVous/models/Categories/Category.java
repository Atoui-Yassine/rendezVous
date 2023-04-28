package com.example.rendezVous.models.Categories;

import com.example.rendezVous.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    private String name;
    @JsonIgnore
    private Long parent;


}
