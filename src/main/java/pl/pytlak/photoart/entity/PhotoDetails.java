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
    private String ISO;

    @Column
    private String camera;

    @Column
    private String model;

    @Column(columnDefinition = "varchar(400000)")
    private String exif;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "photoDetailsId")
    private Photo photo;

    @Column(columnDefinition = "varchar(4000)")
    private String Description;

    public PhotoDetails(Long photoDetailsId, String ISO, String camera, String model, String exif, Photo photo, String description) {
        this.photoDetailsId = photoDetailsId;
        this.ISO = ISO;
        this.camera = camera;
        this.model = model;
        this.exif = exif;
        this.photo = photo;
        Description = description;
    }

    public PhotoDetails() {
    }
}
