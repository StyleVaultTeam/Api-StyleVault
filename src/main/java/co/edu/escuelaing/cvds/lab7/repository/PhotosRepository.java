package co.edu.escuelaing.cvds.lab7.repository;

import co.edu.escuelaing.cvds.lab7.model.Photos;
import co.edu.escuelaing.cvds.lab7.model.PhotosByUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PhotosRepository extends JpaRepository<PhotosByUser, Long> {

    List<PhotosByUser> findByUserName(String userName);
    PhotosByUser findByPhotoId(Long id);

}
