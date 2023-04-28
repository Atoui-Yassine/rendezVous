package com.example.rendezVous.repositories;

import com.example.rendezVous.models.userModel.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ImgRepository extends JpaRepository<Img,Long> {
    @Query("select i from Img i where i.name = ?1")
    Optional<Img> findByName(String name);

}
