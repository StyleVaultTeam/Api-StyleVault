package co.edu.escuelaing.cvds.lab7.model;

import jakarta.persistence.*;

public class UserVault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // Constructor
    public UserVault() {
    }

    // Constructor con parámetros
    public UserVault(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter para el campo 'id'
    public Long getId() {
        return id;
    }

    // Setter para el campo 'id'
    public void setId(Long id) {
        this.id = id;
    }

    // Getter para el campo 'username'
    public String getUsername() {
        return username;
    }

    // Setter para el campo 'username'
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter para el campo 'password'
    public String getPassword() {
        return password;
    }

    // Setter para el campo 'password'
    public void setPassword(String password) {
        this.password = password;
    }
}

