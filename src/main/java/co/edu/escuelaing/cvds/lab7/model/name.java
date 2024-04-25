package co.edu.escuelaing.cvds.lab7.model;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString

public class name {
    private String name;
    public name (String name){
        this.name = name;
    }
}
