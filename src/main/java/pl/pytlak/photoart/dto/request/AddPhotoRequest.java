package pl.pytlak.photoart.dto.request;


import lombok.Getter;

import java.util.List;

@Getter
public class AddPhotoRequest {

    private String title;
    private String description;
    private Long albumId;
    private List<String> tags;
    private String photoUuid;
    private String camera;
    private String model;
    private Long iso;
    private String exif;
}
