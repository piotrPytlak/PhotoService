package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.pytlak.photoart.dto.request.LoginRequest;
import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.entity.Follow;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.service.authentication.AuthenticationService;
import pl.pytlak.photoart.service.user.UserService;
import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.model.AuthenticationModel;
import pl.pytlak.photoart.repository.AuthenticationRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @PutMapping("/follow/{userId}")
    public void follow(@PathVariable("userId") @NotBlank Long userId) {
        userService.makeFollow(userId);
    }


    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest loginRequest) {
    }

    @GetMapping("/logout")
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }


    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {

    }


    //TODO method to show user albums

}
