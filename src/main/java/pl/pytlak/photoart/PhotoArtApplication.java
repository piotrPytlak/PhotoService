package pl.pytlak.photoart;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class PhotoArtApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PhotoArtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
