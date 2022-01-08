package pl.pytlak.photoart.service.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.dto.response.UserPhotoResponse;
import pl.pytlak.photoart.entity.Album;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.entity.UserDetails;
import pl.pytlak.photoart.repository.PhotoRepository;
import pl.pytlak.photoart.service.Validation.ValidationService;
import pl.pytlak.photoart.service.album.AlbumService;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final ValidationService validationService;
    private final PhotoRepository photoRepository;
    private final AuthenticationService authenticationService;
    private final AlbumService albumService;

    public Photo add(Photo photo) {
        return photoRepository.save(photo);
    }

    public void remove(Photo photo) {
        photoRepository.delete(photo);
    }


    public List<UserPhotoResponse> getPhotosByUserId(Long userId) {
        return photoRepository.getUserPhotos(userId, PageRequest.of(0, 7)).stream()
                .map(x -> UserPhotoResponse.builder()
                        .photoId(x.getId())
                        .photoPath(x.getName())
                        .photoTitle(x.getTitle())
                        .creationTime(x.getCreationTime())
                        .ISO(x.getPhotoDetails().getISO())
                        .Model(x.getPhotoDetails().getModel())
                        .Description(x.getPhotoDetails().getDescription())
                        .Camera(x.getPhotoDetails().getCamera())
                        .build()).collect(Collectors.toList());
    }

    public List<UserPhotoResponse> getPhotosByUserIdPull(Long userId, Long photoId) throws Exception {

        Optional<Photo> lastPhoto = photoRepository.getLastPhoto(userId, photoId);

        return lastPhoto.map(x -> photoRepository.getUserPhotosLoad(userId, x.getCreationTime(), PageRequest.of(0, 7)).stream()
                .map(y -> UserPhotoResponse.builder()
                        .photoId(y.getId())
                        .photoPath(y.getName())
                        .photoTitle(y.getTitle())
                        .creationTime(y.getCreationTime())
                        .ISO(y.getPhotoDetails().getISO())
                        .Model(y.getPhotoDetails().getModel())
                        .Description(y.getPhotoDetails().getDescription())
                        .Camera(y.getPhotoDetails().getCamera())
                        .build()).collect(Collectors.toList())).orElseThrow(Exception::new);

    }


    public ResponseEntity<Object> uploadImgOnServer(MultipartFile image, Long albumId, String title) throws IOException, URISyntaxException {

        if (!validationService.validationImage(image))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        URL res = getClass().getClassLoader().getResource("static/images/users_photos");
        Path photoPath = Paths.get(res.toURI());
        User user = authenticationService.getCurrentUser();
        String originalFilename = image.getOriginalFilename();
        Optional<Album> optionalAlbum = albumService.findByAlbumIdAndUserId(albumId, user.getId());

        if (optionalAlbum.isPresent()) {
            Photo photo = Photo.builder()
                    .name(image.getOriginalFilename())
                    .title(title)
                    .album(optionalAlbum.get())
                    .build();

            Long photoId = add(photo).getId();
            File photoFile = new File(photoPath + "\\" + user.getUsername() + "-" + photoId + "-" + originalFilename);

            photo.setName(photoFile.getName());
            add(photo);

            try {
                image.transferTo(photoFile);
            } catch (IOException exception) {
                remove(photo);
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }


}

