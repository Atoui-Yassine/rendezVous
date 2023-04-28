package com.example.rendezVous.controllers;

import com.example.rendezVous.DTOs.request.AddressRequest;
import com.example.rendezVous.DTOs.request.InfoUpdate;
import com.example.rendezVous.models.adress.Address;
import com.example.rendezVous.models.userModel.Img;
import com.example.rendezVous.models.userModel.Profile;
import com.example.rendezVous.models.userModel.UserModel;
import com.example.rendezVous.services.userServices.UserService;
import com.example.rendezVous.storage.services.FilesStoregeService;
import com.example.rendezVous.utils.TokenUtils;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private FilesStoregeService<Img> imageService;
    @Autowired
    private PasswordEncoder encoder;



    @PostMapping
    public ResponseEntity UpdateUserInfo(@RequestBody @NonNull InfoUpdate infoUpdate) {

        UserModel user = userService.findById(tokenUtils.ExtractId());
        if (infoUpdate.getLastname() != null) user.setLastname(infoUpdate.getLastname());
        if (infoUpdate.getFirstname() != null) user.setFirstname(infoUpdate.getFirstname());

        if (infoUpdate.getPassword() != null) user.setPassword(encoder.encode(infoUpdate.getPassword()));
        if (infoUpdate.getGender() != null) user.setGender(infoUpdate.getGender());
        if (infoUpdate.getAdresse() != null) user.setAdresse(user.getAdresse());
        if (infoUpdate.getBio() != null) user.setBio(infoUpdate.getBio());
        if (infoUpdate.getDob() != null) user.setDob(infoUpdate.getDob());
        if (infoUpdate.getPhotoUrl() != null) user.setPhotoUrl(infoUpdate.getPhotoUrl());
        if (infoUpdate.getEduProfile() != null) user.getEduProfile().addAll(infoUpdate.getEduProfile());
        if(!infoUpdate.expProfileNull()) user.getExpProfile().addAll(infoUpdate.getExpProfile());
        if(infoUpdate.getSkiProfile()!=null) user.getSkiProfile().addAll(infoUpdate.getSkiProfile());

        if (infoUpdate.getJobTitle() != null) user.getProfilePro().setJobTitle(infoUpdate.getJobTitle());
        if (infoUpdate.getCoverUrl() != null) user.getProfilePro().setCoverUrl(infoUpdate.getCoverUrl());

        return new ResponseEntity(userService.saveUser(user), HttpStatus.ACCEPTED);

    }

    @SneakyThrows
    @PostMapping("/image")
    public ResponseEntity changeUserImage(@RequestParam("file") MultipartFile file, @RequestParam String name){

        Img img=imageService.store(file);
       UserModel user= userService.findById(tokenUtils.ExtractId());
       user.getImages().add(img);
       switch (name){
           case "profile":
               user.setPhotoUrl(img.getUrl());
               break;
           case "cover":

               user.getProfilePro().setCoverUrl(img.getUrl());
               break;
           default:
               throw new RuntimeException("") ;
       }
        userService.saveUser(user);
        return new  ResponseEntity(img.getName(),HttpStatus.OK);

    }

    @PostMapping("/address")
    public ResponseEntity changeAddress(@RequestBody @Valid AddressRequest addressRequest){
        UserModel user=userService.findById(tokenUtils.ExtractId());
        user.setAddress( new Address( addressRequest.getLar(),  addressRequest.getLan(),  addressRequest.getFullAddress(), addressRequest.getCountry(), addressRequest.getProvince(), addressRequest.getCity()));

        return ResponseEntity.ok(userService.saveUser(user).getAddress());
    }



}
