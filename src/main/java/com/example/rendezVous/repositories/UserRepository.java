package com.example.rendezVous.repositories;

import com.example.rendezVous.models.userModel.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findUserByEmail(String email);
    Boolean existsByEmail(String email) ;
}
