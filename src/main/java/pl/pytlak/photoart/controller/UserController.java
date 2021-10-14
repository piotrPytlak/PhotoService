package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pytlak.photoart.dto.request.LoginRequest;
import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.model.AuthenticationModel;
import pl.pytlak.photoart.repository.AuthenticationRepository;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    private final AuthenticationRepository authenticationService;

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest loginRequest) {
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest registerRequest) {

    }


    //TODO method to show user albums

}
