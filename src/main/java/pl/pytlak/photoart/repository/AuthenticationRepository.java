package pl.pytlak.photoart.repository;

import pl.pytlak.photoart.dto.request.RegisterRequest;
import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.model.AuthenticationModel;

import java.util.Optional;

public interface AuthenticationRepository {

    Optional<User> register(RegisterRequest registerRequest);

    User getCurrentUser();
}
