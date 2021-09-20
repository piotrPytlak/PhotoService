package pl.pytlak.photoart.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.pytlak.photoart.entity.Photo;

@Value
@RequiredArgsConstructor
public class PhotoModel {

    Long id;
    String link;
    String title;
    String description;

    public PhotoModel(Photo photo) {
        id = photo.getId();
        link = photo.getLink();
        title = photo.getTitle();
        description = photo.getDescription();

    }

}
