package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pytlak.photoart.dto.request.AddPhotoRequest;
import pl.pytlak.photoart.dto.request.AlbumAddRequest;
import pl.pytlak.photoart.dto.response.UserAlbumWithThumbnailResponse;
import pl.pytlak.photoart.dto.response.UserAlbumsResponse;
import pl.pytlak.photoart.entity.Album;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.service.album.AlbumService;
import pl.pytlak.photoart.service.authentication.AuthenticationService;
import pl.pytlak.photoart.service.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class AlbumController {

    private final AuthenticationService authenticationService;
    private final AlbumService albumService;
    private final UserService userService;

    @PostMapping("/addAlbum")
    public ResponseEntity<?> add(@Valid @RequestBody AlbumAddRequest albumAddRequest) {
        User user = authenticationService.getCurrentUser();
        Album album = new Album();

        album.setName(albumAddRequest.getName());
        album.setDescription(albumAddRequest.getDescription());
        album.setUser(user);

        try {
            albumService.add(album);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/userAlbums/{userId}")
    public ResponseEntity<List<UserAlbumsResponse>> getUserAlbums(@PathVariable("userId") @NotBlank Long userId) {
        return new ResponseEntity<>(albumService.getAlbumsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/currentUserAlbums")
    public ResponseEntity<List<UserAlbumsResponse>> getCurrentUserAlbums() {
        return new ResponseEntity<>(albumService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/userAlbumsPull")
    public ResponseEntity<List<UserAlbumsResponse>> getUserAlbums(@RequestParam("userId") Long userId, @RequestParam("lastAlbumId") Long lastAlbumId) {
        try {
            return new ResponseEntity<>(albumService.getAlbumsByUserIdPull(userId, lastAlbumId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @PostMapping("/addPhoto")
    public ResponseEntity<?> addPhoto(@RequestBody AddPhotoRequest addPhotoRequest) {
        try {
            System.out.println(addPhotoRequest.getIso());
            userService.addPhoto(addPhotoRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/userAlbumsWithThumbnail/{userId}")
    public ResponseEntity<List<UserAlbumWithThumbnailResponse>> getUserAlbumsWithThumbnails(@PathVariable("userId") @NotBlank Long userId) {
        return new ResponseEntity<>(albumService.getUserAlbumsWithThumbnailsL(userId), HttpStatus.OK);
    }


}
