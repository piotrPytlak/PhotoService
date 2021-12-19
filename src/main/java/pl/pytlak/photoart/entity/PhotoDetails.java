package pl.pytlak.photoart.entity;

import lombok.Builder;
import lombok.Data;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "photoDetailsId")

    private Photo photo;

    @Column(columnDefinition = "varchar(4000)")
    private String Description;

    public PhotoDetails(Long photoDetailsId, Integer ISO, String camera, String model, Photo photo, String description) {
        this.photoDetailsId = photoDetailsId;
        this.ISO = ISO;
        Camera = camera;
        Model = model;
        this.photo = photo;
        Description = description;
    }

    public PhotoDetails() {
    }
}
