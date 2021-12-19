package pl.pytlak.photoart.entitiyKey;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
public class TagPhotoId implements Serializable {

    private Long photoId;
    private Long tagId;


    public TagPhotoId(Long photoId, Long tagId) {
        this.photoId = photoId;
        this.tagId = tagId;
    }

    public TagPhotoId() {
    }
}
