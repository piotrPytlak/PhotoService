package pl.pytlak.photoart.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class PhotoDetails implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
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
