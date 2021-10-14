package pl.pytlak.photoart.service.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.repository.AuthenticationRepository;
import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.model.AuthenticationModel;
import pl.pytlak.photoart.repository.UserRepository;
import pl.pytlak.photoart.security.UserDetailsImpl;

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


}
