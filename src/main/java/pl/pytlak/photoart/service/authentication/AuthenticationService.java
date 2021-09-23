package pl.pytlak.photoart.service.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    public Optional<AuthenticationModel> register(String email, String username, String firstName, String lastName, String password, Integer age, Gender gender) {
        User user = new User(email, username, firstName, lastName, passwordEncoder.encode(password), age, gender);

        try {
            userRepository.save(user);
            return Optional.of(new AuthenticationModel(user));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    @Override
    public User getCurrentUser() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

    }

}
