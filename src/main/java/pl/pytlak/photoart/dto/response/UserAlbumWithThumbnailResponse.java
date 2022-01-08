package pl.pytlak.photoart.dto.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAlbumWithThumbnailResponse {

    String albumName;

    String albumData;

    String photoPath;

    Integer countPhotos;

}
