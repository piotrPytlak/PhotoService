package pl.pytlak.photoart.service.album;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.dto.response.UserAlbumsResponse;
import pl.pytlak.photoart.entity.Album;
import pl.pytlak.photoart.repository.AlbumRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public List<UserAlbumsResponse> getAlbumsByUserId(Long userId) {
        return albumRepository.getUserAlbums(userId, PageRequest.of(0, 5)).stream()
                .map(x -> UserAlbumsResponse.builder()
                        .albumId(x.getId())
                        .name(x.getName())
                        .description(x.getDescription())
                        .createTime(x.getCreationTime())
                        .build()).collect(Collectors.toList());


    }

    public List<UserAlbumsResponse> getAlbumsByUserIdPull(Long userId, Long lastAlbumId) throws Exception {

        Optional<Album> lastAlbum = albumRepository.getLastAlbum(userId, lastAlbumId);

        return lastAlbum.map(x -> albumRepository.getUserAlbumsLoad(userId, x.getCreationTime(), PageRequest.of(0, 5)).stream()
                .map(y -> UserAlbumsResponse.builder()
                        .albumId(y.getId())
                        .name(y.getName())
                        .createTime(y.getCreationTime())
                        .description(y.getDescription())
                        .build()).collect(Collectors.toList())).orElseThrow(Exception::new);

    }
}
