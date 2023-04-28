package com.example.rendezVous.repositories;

import com.example.rendezVous.models.userModel.ERole;
import com.example.rendezVous.models.userModel.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole role);
}
