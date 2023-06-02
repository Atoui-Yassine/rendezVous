package com.example.rendezVous.DTOs.specification;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class UserParams extends PaginationParams {
    private String country;
    private String cat_name;

    private String sortBy="id";
    private String sortDir="asc";

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }



    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        if(sortBy == "Lastname" || sortBy == "Firstname" || sortBy =="id")
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
       // if(sortDir == "asc" || sortDir == "desc")
        this.sortDir = sortDir;
    }

}
