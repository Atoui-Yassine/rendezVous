package com.example.rendezVous.storage.services;

import com.example.rendezVous.models.userModel.Img;
import com.example.rendezVous.repositories.ImgRepository;
import com.example.rendezVous.storage.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class FilesStorageServiceImpl implements FilesStoregeService <Img>{
   @Autowired(required = true)
    ImgRepository imgRepository;


    @Override
    public Img store(MultipartFile file) throws IOException {
        Date date = new Date();
        Img imageData =Img.builder()
                .url("/images/"+date.getTime() )
                .name(String.valueOf(date.getTime()))
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes()))
                .build();

        return imgRepository.save(imageData);
    }

    @Override
    public Img getInfo(Long id) throws NoSuchElementException {
        return imgRepository.findById(id).get();
    }

    @Override
    public byte[] loadImage(Long id) {
        Img imageData = imgRepository.findByName(id.toString()).get();
        return ImageUtil.decompressImage(imageData.getImageData());
    }

}
