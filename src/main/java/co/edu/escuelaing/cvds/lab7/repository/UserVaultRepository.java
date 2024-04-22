package co.edu.escuelaing.cvds.lab7.repository;
import co.edu.escuelaing.cvds.lab7.model.UserVault;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVaultRepository extends JpaRepository<UserVault, Long> {

    UserVault findByUsername(String username);
}

