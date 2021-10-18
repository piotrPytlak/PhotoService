package pl.pytlak.photoart.entity;

import lombok.Data;
import pl.pytlak.photoart.entitiyKey.FavoriteId;

import javax.persistence.*;

@Entity
@Data
@IdClass(FavoriteId.class)
public class Favorite {

    @Id
    @Column(name = "idUser")
    private Long userId;

    @Id
    @Column(name = "idPhoto")
    private Long photoId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @MapsId
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPhoto")
    @MapsId
    private Photo photo;


}
