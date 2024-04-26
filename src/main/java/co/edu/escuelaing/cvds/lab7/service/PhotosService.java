package co.edu.escuelaing.cvds.lab7.service;

import co.edu.escuelaing.cvds.lab7.model.Photos;
import co.edu.escuelaing.cvds.lab7.model.PhotosByUser;
import co.edu.escuelaing.cvds.lab7.repository.PhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class PhotosService {

    private final PhotosRepository photosRepository;

    @Autowired
    public PhotosService(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public List<PhotosByUser> getPhotosByUserName(String userName) {
        return  photosRepository.findByUserName(userName);
    }

    public void savePhoto(PhotosByUser photosByUser) {
        photosRepository.save(photosByUser);
    }

    public void deletePhoto(Long photoId) {
        PhotosByUser photo = photosRepository.findByPhotoId(photoId);
        photosRepository.delete(photo);
    }
}
