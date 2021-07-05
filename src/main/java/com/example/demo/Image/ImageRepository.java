package com.example.demo.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {


    @Query("SELECT s FROM Image s WHERE s.albumId = ?1")
    Optional<List<Image>> findImagesByAlbumId(Integer id);

    @Query("SELECT s FROM Image s WHERE s.id = ?1")
    Optional<Image> findImageById(Long id);
}
