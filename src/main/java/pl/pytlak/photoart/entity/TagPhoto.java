package pl.pytlak.photoart.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.pytlak.photoart.entitiyKey.TagPhotoId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
public class TagPhoto {

    @EmbeddedId
    private TagPhotoId tagPhotoId;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId(value = "photoId")
    @JoinColumn(name = "photoId")
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId(value = "tagId")
    @JoinColumn(name = "tagId")
    private Tag tag;

    public TagPhoto(TagPhotoId tagPhotoId, Photo photo, Tag tag) {
        this.tagPhotoId = tagPhotoId;
        this.photo = photo;
        this.tag = tag;
    }

    public TagPhoto() {
    }
}
