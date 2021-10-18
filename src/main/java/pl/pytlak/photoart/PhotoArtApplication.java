package pl.pytlak.photoart;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pytlak.photoart.type.Gender;
import pl.pytlak.photoart.repository.UserRepository;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.type.UserRoles;

@SpringBootApplication
@RequiredArgsConstructor
public class PhotoArtApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PhotoArtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            User admin = new User();
            admin.setEmail("admin@wp.pl");
            admin.setUsername("admin");
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setAge(22);
            admin.setPassword(passwordEncoder.encode("password123!"));
            admin.setGender(Gender.MALE);
            admin.setRole(UserRoles.ADMIN);
            userRepository.save(admin);
        } catch (Exception ignored) {
        }
    }

}
