package com.example.rendezVous.controllers;


import com.example.rendezVous.models.userModel.Img;
import com.example.rendezVous.storage.services.FilesStoregeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import java.io.IOException;


@RestController
@RequestMapping
public class FileController {

    @Autowired
    private FilesStoregeService<Img> imageService;
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/api/files")
    public Img uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        return imageService.store(file);
    }
    @GetMapping("/images/{id}")
    @PermitAll
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
        byte[] image = imageService.loadImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

}
