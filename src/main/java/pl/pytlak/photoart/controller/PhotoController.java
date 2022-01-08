package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.dto.request.UploadRequest;
import pl.pytlak.photoart.dto.response.UserPhotoResponse;
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
import java.sql.Timestamp;
import java.util.List;


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

    @GetMapping("/userAvatar")
    public String getUserAvatar() {
        User user = authenticationService.getCurrentUser();
        return user.getUserDetails().getAvatarPhoto().getName();

    }

    @GetMapping("/userPhotos/{userId}")
    public ResponseEntity<List<UserPhotoResponse>> getUserPhotos(@PathVariable("userId") @NotBlank Long userId) {
        return new ResponseEntity<>(photoService.getPhotosByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/userPhotosPull")
    public ResponseEntity<List<UserPhotoResponse>> getUserPhotos(@RequestParam("userId") Long userId, @RequestParam("lastPhotoId") Long lastPhotoId) throws Exception {
//        try {
            return new ResponseEntity<>(photoService.getPhotosByUserIdPull(userId, lastPhotoId), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
    }


}



