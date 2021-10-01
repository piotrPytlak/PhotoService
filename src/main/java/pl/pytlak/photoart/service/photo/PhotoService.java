package pl.pytlak.photoart.service.photo;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.repository.PhotoRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final Tika tika;
    private final PhotoRepository photoRepository;

    public Photo add(Photo photo) {
        return photoRepository.save(photo);
    }

    public void remove(Photo photo) {
        photoRepository.delete(photo);
    }

    public boolean validationImage(MultipartFile img) throws IOException {

        String mimeType = tika.detect(img.getBytes());

        return mimeType.matches("image/(jpg|jpeg|png|bmp)$");

    }
}
