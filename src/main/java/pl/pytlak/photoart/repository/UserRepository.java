package pl.pytlak.photoart.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pytlak.photoart.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
