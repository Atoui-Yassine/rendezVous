package com.example.rendezVous.repositories;

import com.example.rendezVous.models.userModel.ERole;
import com.example.rendezVous.models.userModel.Role;
import com.example.rendezVous.models.userModel.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long>, JpaSpecificationExecutor<UserModel> , PagingAndSortingRepository<UserModel,Long> {
    Optional<UserModel> findUserByEmail(String email);
    Boolean existsByEmail(String email) ;
    @Query("SELECT u FROM UserModel u JOIN u.roles r WHERE r.name = :roleName")
    List<UserModel> findByRolesRoleName(ERole roleName);
}
