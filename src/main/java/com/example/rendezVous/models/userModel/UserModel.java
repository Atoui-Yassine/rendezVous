package com.example.rendezVous.models.userModel;

import com.example.rendezVous.models.BaseEntity;
import com.example.rendezVous.models.adress.Address;
import com.example.rendezVous.models.userModel.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserModel extends BaseEntity {

    private String Lastname;

    private String Firstname;

    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;

    private Boolean isPresent = false;

    @Column(length = 3000)
    private String bio;
    private Date dob;
    private String gender;
    private String photoUrl;
    @Column(length = 3000)
    private String adresse;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ExtraInfo> eduProfile = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ExtraInfo> ExpProfile = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private Set<ExtraInfo> skiProfile = new HashSet<>();
    @Embedded
    private Profile profilePro=new Profile();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Img> images=new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    public UserModel(String email, String password) {

        this.email = email;
        this.password = password;
        this.profilePro=new Profile();
    }

    public UserModel(String Lastname, String Firstname, String email, String password) {

        this.Lastname = Lastname;
        this.Firstname = Firstname;
        this.email = email;
        this.password = password;
        this.profilePro=new Profile();
    }

    @PostPersist
    public void init() {
        profilePro = new Profile();

        // set up other fields in myEmbeddedObject here
    }
}
