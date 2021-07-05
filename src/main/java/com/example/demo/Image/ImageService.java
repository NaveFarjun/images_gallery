package com.example.demo.Image;


import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    public List<Image> getImagesList(){
        return imageRepository.findAll();
    }

    public Optional<List<Image>> getImagesByAlbumId(Integer albumId){
        return imageRepository.findImagesByAlbumId(albumId);
    }

    public void addImage(Image image) {
        if(getImgById(image.getId()).isPresent()){
            throw new IllegalArgumentException();
        }
        imageRepository.save(image);
    }

    public Optional<Image> getImgById(Long id) {
        Optional<Image> img = imageRepository.findImageById(id);
        return img;
    }

}
