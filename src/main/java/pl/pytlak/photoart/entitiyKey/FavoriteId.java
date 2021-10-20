package pl.pytlak.photoart.entitiyKey;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class FavoriteId implements Serializable {

    private Long userId;
    private Long photoId;

    public FavoriteId(Long userId, Long photoId) {
        this.userId = userId;
        this.photoId = photoId;
    }

    public FavoriteId(){}
}
