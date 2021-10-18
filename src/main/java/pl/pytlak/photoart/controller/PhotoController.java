package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pytlak.photoart.dto.request.UploadRequest;
import pl.pytlak.photoart.entity.Album;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.service.album.AlbumService;
import pl.pytlak.photoart.service.authentication.AuthenticationService;
import pl.pytlak.photoart.service.photo.PhotoService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class PhotoController {

    private final PhotoService photoService;
    private final AuthenticationService authenticationService;
    private final AlbumService albumService;


    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> upload(@Valid @ModelAttribute UploadRequest uploadRequest) throws URISyntaxException, IOException {


        if (!photoService.validationImage(uploadRequest.getImg()))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        URL res = getClass().getClassLoader().getResource("static/images");
        Path photoPath = Paths.get(res.toURI());
        User user = authenticationService.getCurrentUser();
        String originalFilename = uploadRequest.getImg().getOriginalFilename();
        Optional<Album> optionalAlbum = albumService.findByAlbumIdAndUserId(uploadRequest.getAlbumId(), user.getId());

        if (optionalAlbum.isPresent()) {
            Photo photo = Photo.builder()
                    .name(uploadRequest.getImg().getOriginalFilename())
                    .title(uploadRequest.getTitle())
                    .album(optionalAlbum.get())
                    .build();

            Long photoId = photoService.add(photo).getId();
            File photoFile = new File(photoPath + "\\" + user.getUsername() + "-" + photoId + "-" + originalFilename);

            try {
                uploadRequest.getImg().transferTo(photoFile);
            } catch (IOException exception) {
                photoService.remove(photo);
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

}



