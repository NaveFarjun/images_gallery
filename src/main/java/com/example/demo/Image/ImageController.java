package com.example.demo.Image;

import com.example.demo.ImageResourceHttpRequest.ImageResourceHttpRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "images")
public class ImageController {

    private final ImageService imageService;
    @Resource
    private ImageResourceHttpRequestHandler imageResourceHttpRequestHandler;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    @ResponseBody
    public List<Image> getImagesList(Model model){
        return this.imageService.getImagesList();
    }

    @GetMapping(value = "download/{id}", produces = MediaType.ALL_VALUE)
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Long id)
            throws ServletException, IOException {
        Optional<Image> optionalImage = imageService.getImgById(id);
        if(optionalImage.isPresent()){
            Image image = optionalImage.get();
            File file = new File(image.getLocalPath());
            httpServletRequest.setAttribute(ImageResourceHttpRequestHandler.ATTRIBUTE_FILE, file);
            imageResourceHttpRequestHandler.handleRequest(httpServletRequest, httpServletResponse);
        }
        else{
            httpServletResponse.sendError(400,"Image not exists");
        }
    }


    @GetMapping("/album")
    @ResponseBody
    public List<Image> getImagesByAlbumId(@RequestParam Integer albumId){
        List<Image> listToReturn = new ArrayList<>();
        if(albumId != null) {
            Optional<List<Image>> albumImages = this.imageService.getImagesByAlbumId(albumId);
            albumImages.ifPresent(listToReturn::addAll);
        }
        return listToReturn;
    }

    @PostMapping
    public void addImage(@RequestBody Image image){
        this.imageService.addImage(image);
    }


}
