package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.service.authentication.AuthenticationService;
import pl.pytlak.photoart.service.photo.PhotoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class PhotoController {

    private final PhotoService photoService;
    private final AuthenticationService authenticationService;

    @PostMapping("/test")
    public ResponseEntity<Object> upload(@RequestPart String title, @RequestPart MultipartFile img) throws IOException {

        User user = authenticationService.getCurrentUser();
        String originalFilename = img.getOriginalFilename();

        String imgName = photoService.createName(user, originalFilename);
        File photoFile = new File("C:\\Projects\\PhotoArt\\src\\main\\resources\\static\\images\\" + imgName);
        FileOutputStream fileOutputStream = new FileOutputStream(photoFile);

        fileOutputStream.write(img.getBytes());
        fileOutputStream.close();

        Photo photo = new Photo();
        photo.setTitle(title);
        photo.setLink(photoFile.getAbsolutePath());
        photoService.add(photo);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
