package pl.pytlak.photoart.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPhotoResponse {

    Long photoId;

    String photoPath;

    String photoTitle;

    Timestamp creationTime;

    String ISO;

    String Camera;

    String Model;

    String Description;


}
