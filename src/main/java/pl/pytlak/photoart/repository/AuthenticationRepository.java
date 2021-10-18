package pl.pytlak.photoart.repository;

import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.entity.User;

import java.util.Optional;

public interface AuthenticationRepository {

    Optional<User> register(RegisterRequest registerRequest);

    User getCurrentUser();
}
