package com.example.rendezVous.services.userServices;

import com.example.rendezVous.models.userModel.ERole;
import com.example.rendezVous.models.userModel.Role;
import com.example.rendezVous.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> findByName(ERole name){
        return  roleRepository.findByName(name);
    }
    public Role findbyNameOrCreate(ERole name){
        return findByName(name)
                .orElseGet(() -> roleRepository.save(new Role(null,name)));
    }
}
