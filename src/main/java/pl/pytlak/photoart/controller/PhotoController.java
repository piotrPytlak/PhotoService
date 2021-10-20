package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.dto.request.UploadRequest;
import pl.pytlak.photoart.service.photo.PhotoService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> upload(@Valid @ModelAttribute UploadRequest uploadRequest) throws URISyntaxException, IOException {

        MultipartFile img = uploadRequest.getImg();
        Long albumId = uploadRequest.getAlbumId();
        String title = uploadRequest.getTitle();

        return photoService.uploadImgOnServer(img, albumId, title);
    }
}



