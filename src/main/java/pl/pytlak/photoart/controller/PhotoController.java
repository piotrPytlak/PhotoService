package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.dto.request.UploadRequest;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.entity.UserDetails;
import pl.pytlak.photoart.repository.PhotoRepository;
import pl.pytlak.photoart.repository.UserDetailsRepository;
import pl.pytlak.photoart.service.authentication.AuthenticationService;
import pl.pytlak.photoart.service.photo.PhotoService;
import pl.pytlak.photoart.service.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URISyntaxException;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class PhotoController {

    private final PhotoService photoService;
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> upload(@Valid @ModelAttribute UploadRequest uploadRequest) throws URISyntaxException, IOException {

        MultipartFile img = uploadRequest.getImg();
        Long albumId = uploadRequest.getAlbumId();
        String title = uploadRequest.getTitle();

        return photoService.uploadImgOnServer(img, albumId, title);
    }

    //?
    @GetMapping("/userAvatar")
    public String getUserAvatar() {
        User user = authenticationService.getCurrentUser();
        return user.getUserDetails().getAvatarPhoto().getName();

    }
}



