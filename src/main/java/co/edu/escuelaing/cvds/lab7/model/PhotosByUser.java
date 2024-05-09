package co.edu.escuelaing.cvds.lab7.model;

import java.net.URL;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "PhotosByUser")
public class PhotosByUser {
    @Id
    @Column(name = "PhotoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;
    @Column(name = "userName")
    private String userName;
    @Column(name = "typeClothe")
    private String typeClothe;
    @Column(name = "photo64",length = 200000)
    private String photo64;
}
