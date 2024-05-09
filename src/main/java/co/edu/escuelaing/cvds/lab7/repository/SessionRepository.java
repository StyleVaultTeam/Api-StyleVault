package co.edu.escuelaing.cvds.lab7.repository;

import co.edu.escuelaing.cvds.lab7.model.Session;
import co.edu.escuelaing.cvds.lab7.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Session findByToken(UUID token);
    Session findByUser(User user);
}