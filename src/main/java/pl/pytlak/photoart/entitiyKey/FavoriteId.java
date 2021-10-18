package pl.pytlak.photoart.entitiyKey;

import lombok.Data;

import java.io.Serializable;

@Data
public class FavoriteId implements Serializable {

    private Long userId;
    private Long photoId;

}
