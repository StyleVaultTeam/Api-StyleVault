package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.PhotosByUser;
import co.edu.escuelaing.cvds.lab7.service.PhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotosController {

    private final PhotosService photosService;

    @Autowired
    public PhotosController(PhotosService photosService) {
        this.photosService = photosService;
    }

    @GetMapping("/{userName}")
    public ResponseEntity     getPhotosByUserName(@PathVariable String userName) {
        try {
            List<PhotosByUser> photos = photosService.getPhotosByUserName(userName);
            return ResponseEntity.ok(photos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Photos not found for user: " + userName);
        }
    }

    @PostMapping
    public ResponseEntity savePhoto(@RequestBody PhotosByUser photosByUser) {
        try {
            photosService.savePhoto(photosByUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving photo");
        }
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity deletePhoto(@PathVariable Long photoId) {
        try {
            photosService.deletePhoto(photoId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Photo with id " + photoId + " not found");
        }
    }
}
