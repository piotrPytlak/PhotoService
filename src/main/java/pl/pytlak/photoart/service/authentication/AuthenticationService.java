package pl.pytlak.photoart.service.authentication;

import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.model.AuthenticationModel;

import java.util.Optional;

public interface AuthenticationService {

    Optional<AuthenticationModel> login(String email, String password);
    Optional<AuthenticationModel> register(String email, String firstName, String lastName, String password, Integer age, Gender gender);

    User getCurrentUser();
}
