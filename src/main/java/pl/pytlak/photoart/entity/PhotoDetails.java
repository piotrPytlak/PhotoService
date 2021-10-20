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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "photoDetailsId")
    private Photo photo;

    @Column
    private String ISO;

    @Column
    private String Camera;

    @Column
    private String Flash;

    @Column
    private String Description;

    public PhotoDetails(Long photoDetailsId, Photo photo, String ISO, String camera, String flash, String description) {
        this.photoDetailsId = photoDetailsId;
        this.photo = photo;
        this.ISO = ISO;
        Camera = camera;
        Flash = flash;
        Description = description;
    }

    public PhotoDetails() {
    }
}
