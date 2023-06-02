package com.example.rendezVous.models.userModel;

import com.example.rendezVous.DTOs.views.View;
import com.example.rendezVous.models.BaseEntity;
import com.example.rendezVous.models.Categories.Category;
import com.example.rendezVous.models.adress.Address;
import com.example.rendezVous.models.rendezVous.RendezVous;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(View.base.class)
    @Column(name = "Lastname")
    private String Lastname;
    @JsonView(View.base.class)
    private String Firstname;
    @JsonView(View.base.class)
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;

    private Boolean verifier = false;

    @Column(length = 3000)
    private String bio;

    private Date dob;
    private String gender;
    private String photoUrl;

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
    @OneToOne( cascade = CascadeType.ALL)
    private Address address;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "client")
    private List<RendezVous> c_rendezVous;
    @OneToMany(mappedBy = "userPro")
    private List<RendezVous> p_rendezVous;
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
