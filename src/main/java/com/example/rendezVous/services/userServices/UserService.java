package com.example.rendezVous.services.userServices;

import com.example.rendezVous.DTOs.specification.UserParams;
import com.example.rendezVous.DTOs.specification.UserSpec;
import com.example.rendezVous.exceptions.EntityNotFoundException;

import com.example.rendezVous.models.userModel.ERole;
import com.example.rendezVous.models.userModel.UserModel;
import com.example.rendezVous.repositories.CategoryRepository;
import com.example.rendezVous.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    public UserModel saveUser(UserModel user){return  userRepository.save(user);}
    public UserModel findById(Long id){

        UserModel user= userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user Not Found with id : " + id));

        return user;
        }
    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void setIsChecked(UserModel user, Boolean stat) {
        user.setVerifier(stat);

        userRepository.save(user);
    }

    public Boolean isChecked(UserModel user) {
        return user.getVerifier();
    }



    public Page<UserModel> getProfiles(UserParams userParams) {
        UserSpec userSpec=new UserSpec(userParams);
        Pageable pageable=UserSpec.getPageable(userParams.getPageNumber(), userParams.getPageSize());
        return userRepository.findAll(userSpec,pageable);
    }

    public List<UserModel> getAllUserPro(){
        return userRepository.findByRolesRoleName(ERole.ROLE_PRO);
    }



}
