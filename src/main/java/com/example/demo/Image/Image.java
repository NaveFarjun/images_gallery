package com.example.demo.Image;


import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table
public class Image {

    @Id
    @Column(name = "imageId")
    private Long id;
    private String localPath;
    private String title;
    private Integer albumId;
    private String url;
    private String thumbnailUrl;
    private LocalDateTime downloadedDateTime;
    private Long fileSize;

    public Image(){}

    public void setDownloadedDateTime(LocalDateTime downloadedDateTime) {
        this.downloadedDateTime = downloadedDateTime;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public LocalDateTime getDownloadedDateTime() {
        return downloadedDateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", localPath='" + localPath + '\'' +
                ", title='" + title + '\'' +
                ", albumId=" + albumId +
                ", url='" + url + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", downloadedDateTime=" + downloadedDateTime +
                ", fileSize=" + fileSize +
                '}';
    }

    public void downloadImageToFolder(String folderPath) throws IOException {
        InputStream in = new URL(this.getUrl()).openStream();
        String[] imageParts = this.getUrl().split("\\.");
        String extension = imageParts[imageParts.length-1];
        String localPath = folderPath+"/"+this.getTitle()+"."+extension;
        long imgSize = Files.copy(in, Paths.get(localPath));
        this.setLocalPath(localPath);
        this.setDownloadedDateTime(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        this.setFileSize(imgSize);
    }
}
