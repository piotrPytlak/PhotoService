package pl.pytlak.photoart.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@Builder
public class PhotoDetails {

    @Id
    private Long photoDetailsId;


    @Column
    private Integer ISO;

    @Column
    private String Camera;

    @Column
    private String Model;

    @Column(columnDefinition = "varchar(4000)")
    private String Description;

    public PhotoDetails(Long photoDetailsId, Integer ISO, String camera, String model, String description) {
        this.photoDetailsId = photoDetailsId;
        this.ISO = ISO;
        Camera = camera;
        Model = model;
        Description = description;
    }

    public PhotoDetails() {
    }
}
