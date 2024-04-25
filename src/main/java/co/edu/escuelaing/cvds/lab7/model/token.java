package co.edu.escuelaing.cvds.lab7.model;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
public class token {
    private String token;
    public token(String token){
        this.token = token;
    }
}
