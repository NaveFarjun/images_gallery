package com.example.demo.Image;

import com.example.demo.utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Configuration
public class ImageConfig {

    private final String images_url = "https://shield-j-test.s3.amazonaws.com/photo.txt";
    private final String images_folder_path = "./images_folder";

    @Bean
    CommandLineRunner commandLineRunner(ImageRepository imageRepository){
        return args -> {
            //creating new folder for the downloaded images
            File f1 = new File("./images_folder");
            if(f1.exists()){
                Utils.deleteDirectory(f1);
            }
            f1.mkdir();
            //fetching data from another platform
            HttpResponse<String> response = Utils.sendHttpRequestAndGetTheResponse(new URI(images_url));

            ObjectMapper mapper = new ObjectMapper();
            List<Image> images = mapper.readValue(response.body(), new TypeReference<List<Image>>(){ });
            images.forEach(img->{
                try {
                    img.downloadImageToFolder(images_folder_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            imageRepository.saveAll(images);
        };
    }
}
