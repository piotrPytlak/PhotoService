package pl.pytlak.photoart.entity;

import lombok.Data;
import pl.pytlak.photoart.entitiyKey.FavoriteId;

import javax.persistence.*;

@Entity
@Data
public class Favorite {

    @EmbeddedId
    private FavoriteId favoriteId;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "photoId")
    @JoinColumn(name = "idPhoto")
    private Photo photo;

    public Favorite(FavoriteId favoriteId, User user, Photo photo) {
        this.favoriteId = favoriteId;
        this.user = user;
        this.photo = photo;
    }

    public Favorite() {

    }
}
