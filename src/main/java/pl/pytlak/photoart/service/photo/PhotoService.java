package pl.pytlak.photoart.service.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
import java.util.Optional;

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


    public ResponseEntity<Object> uploadImgOnServer(MultipartFile image, Long albumId, String title) throws IOException, URISyntaxException {

        if (!validationService.validationImage(image))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        URL res = getClass().getClassLoader().getResource("static/images");
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

