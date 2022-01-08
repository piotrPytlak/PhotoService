package pl.pytlak.photoart.entity;

import lombok.*;
import pl.pytlak.photoart.entitiyKey.RateId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
public class Rate {


    @EmbeddedId
    RateId rateId;

    @Column(nullable = false)
    private Float rateValue;


    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false)
    @MapsId("categoryId")
    private RateCategories rateCategories;

    @ManyToOne
    @JoinColumn(name = "photoId", referencedColumnName = "id", nullable = false)
    @MapsId("photoId")
    private Photo photo;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @MapsId("userId")
    private User user;


    public Rate(RateId rateId, Float rateValue, RateCategories rateCategories, Photo photo, User user) {
        this.rateId = rateId;
        this.rateValue = rateValue;
        this.rateCategories = rateCategories;
        this.photo = photo;
        this.user = user;
    }

    public Rate() {
    }
}
