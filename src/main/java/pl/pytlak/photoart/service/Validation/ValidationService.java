package pl.pytlak.photoart.service.Validation;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final Tika tika;

    public boolean validationImage(MultipartFile img) throws IOException {

        String mimeType = tika.detect(img.getBytes());

        return mimeType.matches("image/(jpg|jpeg|png|bmp)$");

    }

}
