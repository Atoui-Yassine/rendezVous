package com.example.rendezVous.models.Categories;

import com.example.rendezVous.models.BaseEntity;
import com.example.rendezVous.models.userModel.Img;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
<<<<<<< Updated upstream
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
=======
>>>>>>> Stashed changes
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category extends BaseEntity {

<<<<<<< Updated upstream

    @Column(name = "name", nullable = false)
    private String name;
   // @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
=======
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "parentid")
    private Category parent;
    @OneToMany(mappedBy = "parent")
    private Set<Category> childCategories;
>>>>>>> Stashed changes

   // @JsonManagedReference
    @OneToMany(mappedBy = "parent")
    private Set<Category> childCategories=new HashSet<>();

    @OneToOne
    private Img image;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Set<Category> childCategories) {
        this.childCategories = childCategories;
    }

    public Img getImage() {
        return image;
    }

    public void setImage(Img image) {
        this.image = image;
    }
}
