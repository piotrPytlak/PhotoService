package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pytlak.photoart.dto.request.LoginRequest;
import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.model.AuthenticationModel;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest loginRequest) {
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationModel> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();
        String password = registerRequest.getPassword();
        Integer age = registerRequest.getAge();
        Gender gender = registerRequest.getGender();

        Optional<AuthenticationModel> authenticationModelOptional = authenticationService.register(email, firstName, lastName, password, age, gender);
        return authenticationModelOptional.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

}
