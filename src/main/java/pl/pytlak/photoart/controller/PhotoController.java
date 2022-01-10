package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.Exception.NoContentException;
import pl.pytlak.photoart.dto.request.AvatarImage;
import pl.pytlak.photoart.dto.request.UploadRequest;
import pl.pytlak.photoart.dto.response.CategoryAvgPhotoRateResponse;
import pl.pytlak.photoart.dto.response.UserPhotoResponse;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.service.authentication.AuthenticationService;
import pl.pytlak.photoart.service.photo.PhotoService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URISyntaxException;
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

    @PostMapping(value = "/uploadAvatar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadAvatar(@Valid @ModelAttribute AvatarImage avatarImage) {

        try {
            photoService.uploadAvatar(avatarImage.getFiles());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/uploadBackground", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadBackground(@Valid @ModelAttribute AvatarImage avatarImage) {

        try {
            photoService.uploadBackground(avatarImage.getFiles());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/photosInUserAlbum")
    public ResponseEntity<List<UserPhotoResponse>> getAlbumPhotosByAlbumId(@RequestParam("userId") Long userId, @RequestParam("albumId") Long albumId) {
        return new ResponseEntity<>(photoService.getPhotosByUserIdAndAlbumId(userId, albumId), HttpStatus.OK);
    }

    @GetMapping("/userPhotos/{userId}")
    public ResponseEntity<List<UserPhotoResponse>> getUserPhotos(@PathVariable("userId") @NotBlank Long userId) {
        return new ResponseEntity<>(photoService.getPhotosByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/userAllPhotos/{userId}")
    public ResponseEntity<List<UserPhotoResponse>> getAllUserPhotos(@PathVariable("userId") @NotBlank Long userId) {
        return new ResponseEntity<>(photoService.getAllPhotosByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/userPhotosPull")
    public ResponseEntity<List<UserPhotoResponse>> getUserPhotos(@RequestParam("userId") Long userId, @RequestParam("lastPhotoId") Long lastPhotoId) throws Exception {
//        try {
        return new ResponseEntity<>(photoService.getPhotosByUserIdPull(userId, lastPhotoId), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
    }


    @PostMapping(value = "/uploadBuffer", produces = {"text/plain"})
    public ResponseEntity<String> uploadBuffer(@RequestPart("files") MultipartFile img) {
        try {
            return new ResponseEntity<>(photoService.handlerFileBuffer(img), HttpStatus.OK);
        } catch (URISyntaxException | IOException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/uploadBuffer")
    public ResponseEntity<?> clearBuffer(@RequestBody String uuid) {
        try {
            photoService.clearBuffer(uuid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (URISyntaxException | IOException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/getPhotoInformation/{photoId}")
    public ResponseEntity<CategoryAvgPhotoRateResponse> getPhotoInformation(@PathVariable @NotBlank Long photoId) {

        try {
            return new ResponseEntity<>(photoService.getPhotoInformationByPhotoId(photoId), HttpStatus.OK);
        } catch (NoContentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/albumPhotosPull")
    public ResponseEntity<List<UserPhotoResponse>> getAlbumPhotos(@RequestParam("userId") Long userId, @RequestParam("albumId") Long albumId, @RequestParam("lastPhotoId") Long lastPhotoId) {
        return new ResponseEntity<>(photoService.getPhotosByAlbumIdAndUserId(albumId, userId, lastPhotoId), HttpStatus.OK);
    }

}



