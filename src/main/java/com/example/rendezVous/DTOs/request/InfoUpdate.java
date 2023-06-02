package com.example.rendezVous.DTOs.request;

import com.example.rendezVous.models.userModel.ExtraInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
public class InfoUpdate {
    private String Lastname;

    private String Firstname;

   @JsonIgnore
    private String password;
    private String gender ;
    private String bio ;
    private String jobTitle ;
    private Date dob ;
    private Integer duration;



    private Double priceHeure;
    private  String photoUrl ;


    private List<String> eduProfile;
    private List<String> ExpProfile;
    private List<String> skiProfile;
    private String coverUrl;


    public  static Set<ExtraInfo> toExtraInfo(List<String> list){
        if(list ==null) return new HashSet<>();
        return list.stream().map(x->new ExtraInfo(x)).collect(Collectors.toSet());

    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }



    public Set<ExtraInfo> getEduProfile() {
        return toExtraInfo(eduProfile);
    }

    public void setEduProfile(List<String> eduProfile) {
        this.eduProfile = eduProfile;
    }
    public boolean expProfileNull() {
        return ExpProfile ==null;
    }
    public Set<ExtraInfo> getExpProfile() {
        return toExtraInfo(ExpProfile);
    }

    public void setExpProfile(List<String> expProfile) {
        ExpProfile = expProfile;
    }

    public Set<ExtraInfo> getSkiProfile() {
        return toExtraInfo(skiProfile);
    }

    public void setSkiProfile(List<String> skiProfile) {
        this.skiProfile = skiProfile;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPriceHeure() {
        return priceHeure;
    }

    public void setPriceHeure(Double priceHeure) {
        this.priceHeure = priceHeure;
    }
}
