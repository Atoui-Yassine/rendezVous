package com.example.rendezVous.services.userServices;

import com.example.rendezVous.exceptions.EntityNotFoundException;
import com.example.rendezVous.models.userModel.UserModel;
import com.example.rendezVous.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserModel saveUser(UserModel user){return  userRepository.save(user);}
    public UserModel findById(Long id){

        UserModel user= userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user Not Found with id : " + id));

        return user;
        }
    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void setIsPresent(UserModel user, Boolean stat) {
        user.setIsPresent(stat);

        userRepository.save(user);
    }

    public Boolean isPresent(UserModel user) {
        return user.getIsPresent();
    }


}
