package pl.pytlak.photoart.service.album;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.entity.Album;
import pl.pytlak.photoart.repository.AlbumRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public Album add(Album album) {
        return albumRepository.save(album);
    }

    public Optional<Album> findByAlbumIdAndUserId(Long albumId, Long userId) {
        return albumRepository.findByIdAndUserId(albumId, userId);
    }


}
