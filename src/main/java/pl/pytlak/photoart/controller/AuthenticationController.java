package pl.pytlak.photoart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

@SuppressWarnings("unchecked")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/check")
    public ResponseEntity<GrantedAuthority> checkRole() {

        return authenticationService.getCurrentPrincipalAuthorities().stream()
                .findAny()
                .map(x -> new ResponseEntity(x, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.UNAUTHORIZED));
    }


}
