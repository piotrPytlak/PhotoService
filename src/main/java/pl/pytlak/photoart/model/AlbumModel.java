package pl.pytlak.photoart.model;


import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.pytlak.photoart.entity.Album;

import java.util.List;
import java.util.stream.Collectors;

@Value
@RequiredArgsConstructor
public class AlbumModel {

    Long id;
    String name;
    String description;
    List<PhotoModel> photos;

    public AlbumModel(Album album){
        id = album.getId();
        name = album.getName();
        description = album.getDescription();
        photos = album.getPhotos().stream()
                .map(PhotoModel::new)
                .collect(Collectors.toList());
    }

}
