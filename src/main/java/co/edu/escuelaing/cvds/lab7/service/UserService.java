package co.edu.escuelaing.cvds.lab7.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import co.edu.escuelaing.cvds.lab7.model.UserVault;
import co.edu.escuelaing.cvds.lab7.repository.UserVaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserVaultRepository userRepository;

    public boolean authenticate(String username, String password) {
        // Buscar el usuario por nombre de usuario
        UserVault user = userRepository.findByUsername(username);

        // Verificar si el usuario existe y si la contraseña coincide
        return user != null && verifyPassword(password, user.getPassword());
    }

    private String hashPassword(String password) {
        try {
            // Crear una instancia de MessageDigest con el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Aplicar el cifrado a la contraseña y obtener el arreglo de bytes resultante
            byte[] hashBytes = digest.digest(password.getBytes());
            // Convertir los bytes a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Manejo de la excepción
            return null;
        }
    }

    private boolean verifyPassword(String inputPassword, String storedPasswordHash) {
        String inputPasswordHash = hashPassword(inputPassword);
        // Comparar la contraseña introducida con la contraseña almacenada
        return inputPasswordHash != null && inputPasswordHash.equals(storedPasswordHash);
    }
}

