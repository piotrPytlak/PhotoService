package pl.pytlak.photoart.service.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.repository.AuthenticationRepository;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.repository.UserRepository;
import pl.pytlak.photoart.security.UserDetailsImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationRepository {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> register(RegisterRequest registerRequest) {
        User user = new User(registerRequest.getEmail(), registerRequest.getUsername(), registerRequest.getFirstName(), registerRequest.getLastName(),
                passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getAge(), registerRequest.getGender());

        try {
            return Optional.of(userRepository.save(user));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    @Override
    public User getCurrentUser() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

    }

    public boolean isAuthenticated() {
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser");

    }

    public Collection<? extends GrantedAuthority> getCurrentPrincipalAuthorities() {

        try {
            return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
        } catch (ClassCastException e) {
            return Collections.emptyList();
        }


    }
}
