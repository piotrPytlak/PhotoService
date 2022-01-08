package pl.pytlak.photoart.entitiyKey;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@Builder
public class RateId implements Serializable {


    private Long userId;

    private Long categoryId;

    private Long photoId;


    public RateId(Long userId, Long categoryId, Long photoId) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.photoId = photoId;
    }

    public RateId() {
    }
}
