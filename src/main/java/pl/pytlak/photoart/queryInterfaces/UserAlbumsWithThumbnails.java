package pl.pytlak.photoart.queryInterfaces;

import java.sql.Timestamp;

public interface UserAlbumsWithThumbnails {

    Long getAlbumId();

    String getAlbumName();

    Integer getCountPhotos();

    Timestamp getAlbumData();

    String getPhotoPath();

}
