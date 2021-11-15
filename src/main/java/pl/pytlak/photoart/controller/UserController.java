package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.dto.request.LoginRequest;
import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.dto.response.SearchUserResponse;
import pl.pytlak.photoart.dto.response.UserInformation;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.service.photo.PhotoService;
import pl.pytlak.photoart.service.user.UserDetailsService;
import pl.pytlak.photoart.service.user.UserService;
import pl.pytlak.photoart.type.UserDetailsImg;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/search")
    public ResponseEntity<List<SearchUserResponse>> searchBar(@RequestParam("username") @NotBlank String searchParam) {

        if (searchParam.length() >= 3)
            return new ResponseEntity<>(userService.searchUsers(searchParam), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/follow/{userId}")
    public void follow(@PathVariable("userId") @NotBlank Long userId) {
        userService.makeFollow(userId);
    }

    @PostMapping("/uploadAvatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("imageFile") MultipartFile avatar) throws IOException, URISyntaxException {
        return userDetailsService.uploadUserDetailsImg(avatar, UserDetailsImg.AVATAR);
    }

    @PostMapping("/uploadBackground")
    public ResponseEntity<?> uploadBackground(@RequestParam("imageFile") MultipartFile background) throws IOException, URISyntaxException {
        return userDetailsService.uploadUserDetailsImg(background, UserDetailsImg.BACKGROUND);
    }


    @GetMapping("/getUserInformation/{userId}")
    public ResponseEntity<UserInformation> getUserInformation(@PathVariable("userId") @NotBlank Long userId) {
        return userDetailsService.getUserInformation(userId);

    }

    @GetMapping("/getCurrentUserInformation")
    public ResponseEntity<UserInformation> getCurrentUserInformation() {
        return userDetailsService.getCurrentUser();
    }


    //TODO method to show user albums

}
