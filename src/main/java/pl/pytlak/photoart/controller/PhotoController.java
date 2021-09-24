package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@Valid @RequestBody UploadRequest uploadRequest, @RequestParam MultipartFile img) {

        Path photoPath = Paths.get("C:\\Projects\\PhotoArt\\src\\main\\resources\\static\\images\\");
        User user = authenticationService.getCurrentUser();
        String originalFilename = img.getOriginalFilename();
        Optional<Album> optionalAlbum = albumService.findByAlbumIdAndUserId(uploadRequest.getAlbumId(), user.getId());

        if (optionalAlbum.isPresent()) {
            Photo photo = Photo.builder()
                    .name(img.getOriginalFilename())
                    .title(uploadRequest.getTitle())
                    .album(optionalAlbum.get())
                    .build();

            Long photoId = photoService.add(photo).getId();
            File photoFile = new File(photoPath + user.getUsername() + "-" + photoId + "-" + originalFilename);

            try {
                img.transferTo(photoFile);
            } catch (IOException exception) {
                photoService.remove(photo);
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
