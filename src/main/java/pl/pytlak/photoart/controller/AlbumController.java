package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pytlak.photoart.dto.request.AlbumAddRequest;
import pl.pytlak.photoart.entity.Album;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.service.album.AlbumService;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class AlbumController {

    private final AuthenticationService authenticationService;
    private final AlbumService albumService;

    @PostMapping("/album")
    public void add(@Valid @RequestBody AlbumAddRequest albumAddRequest) {
        User user = authenticationService.getCurrentUser();
        Album album = new Album();

        album.setName(albumAddRequest.getName());
        album.setDescription(albumAddRequest.getDescription());
        album.setUser(user);

        albumService.add(album);

    }


}
