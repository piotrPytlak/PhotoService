package pl.pytlak.photoart.service.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.repository.PhotoRepository;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public Photo add(Photo photo) {
        return photoRepository.save(photo);
    }

    public void remove(Photo photo) {
        photoRepository.delete(photo);
    }
}
