package pl.pytlak.photoart;

import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

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
