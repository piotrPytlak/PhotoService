package pl.pytlak.photoart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class PhotoDetails implements Serializable {

    @Id
    private Long idPhoto;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "idPhoto", referencedColumnName = "id")
    private Photo photo;

    @Column
    private String ISO;

    @Column
    private String Camera;

    @Column
    private String Flash;

    @Column
    private String Description;


}
