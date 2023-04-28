package com.example.rendezVous.models.userModel;

import com.example.rendezVous.DTOs.views.View;
import com.example.rendezVous.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtraInfo extends BaseEntity {


    private  String description  ;




}
