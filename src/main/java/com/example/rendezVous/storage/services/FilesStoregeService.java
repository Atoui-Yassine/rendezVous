package com.example.rendezVous.storage.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStoregeService<T> {


    public T store(MultipartFile file) throws IOException;
    public T getInfo(Long id);
    public byte[] loadImage(Long id);

}
