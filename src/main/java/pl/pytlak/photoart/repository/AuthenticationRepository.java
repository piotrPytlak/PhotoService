package pl.pytlak.photoart.repository;

import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.model.AuthenticationModel;

import java.util.Optional;

public interface AuthenticationRepository {

    Optional<AuthenticationModel> register(String email, String username, String firstName, String lastName, String password, Integer age, Gender gender);

    User getCurrentUser();
}
