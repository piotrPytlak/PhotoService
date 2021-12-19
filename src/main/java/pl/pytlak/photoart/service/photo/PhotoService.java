package pl.pytlak.photoart.service.photo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.aspectj.util.FileUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.dto.request.AddPhotoRequest;
import pl.pytlak.photoart.dto.response.UserPhotoResponse;
import pl.pytlak.photoart.entitiyKey.TagPhotoId;
import pl.pytlak.photoart.entity.*;
import pl.pytlak.photoart.repository.*;
import pl.pytlak.photoart.service.Validation.ValidationService;
import pl.pytlak.photoart.service.album.AlbumService;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final ValidationService validationService;
    private final PhotoRepository photoRepository;
    private final AuthenticationService authenticationService;
    private final AlbumService albumService;
    private final AlbumRepository albumRepository;
    private final TagRepository tagRepository;
    private final PhotoDetailsRepository photoDetailsRepository;
    private final PhotoTagRepository photoTagRepository;

    public Photo add(Photo photo) {
        return photoRepository.save(photo);
    }

    public void remove(Photo photo) {
        photoRepository.delete(photo);
    }


    public void addPhoto(AddPhotoRequest addPhotoRequest, User user) {

        List<Tag> tagList = tagRepository.findTagByNameList(addPhotoRequest.getTags());

        Function<Album, PhotoDetails> createPhoto = album -> {

            PhotoDetails photoDetails = photoDetailsRepository.save(PhotoDetails.builder()
                    .photo(Photo.builder()
                            .name(addPhotoRequest.getPhotoUuid())
                            .title(addPhotoRequest.getTitle())
                            .album(album)
                            .build())
                    .Camera(addPhotoRequest.getCamera())
                    .ISO(addPhotoRequest.getISO())
                    .Model(addPhotoRequest.getModel())
                    .Description(addPhotoRequest.getDescription())
                    .build());

            photoTagRepository.saveAll(
                    new HashSet<>(addPhotoRequest.getTags()).stream()
                            .map(tag -> tagList.stream()
                                    .filter(x -> x.getTagName().equals(tag))
                                    .findAny().orElseGet(
                                            () -> tagRepository.save(Tag.builder()
                                                    .tagName(tag)
                                                    .build())))
                            .map(tag -> TagPhoto.builder()
                                    .tagPhotoId(TagPhotoId.builder()
                                            .tagId(tag.getId())
                                            .photoId(photoDetails.getPhoto().getId())
                                            .build())
                                    .photo(photoDetails.getPhoto())
                                    .tag(tag)
                                    .build())
                            .collect(Collectors.toList()));

            return photoDetails;
        };


        albumRepository.getAlbumsByIdAndUserId(addPhotoRequest.getAlbumId(), user.getId()).ifPresentOrElse(
                album -> createPhoto.apply(album),
                () -> {

                    Album album = albumRepository.findAlbumByNameAndUserId("others", user.getId())
                            .orElse(albumRepository.save(Album.builder()
                                    .name("others")
                                    .build()));

                    createPhoto.apply(album);
                }
        );

    }

    public List<UserPhotoResponse> getPhotosByUserId(Long userId) {
        return photoRepository.getUserPhotos(userId, PageRequest.of(0, 7)).stream()
                .map(x -> UserPhotoResponse.builder()
                        .photoId(x.getId())
                        .photoPath(x.getName())
                        .photoTitle(x.getTitle())
                        .creationTime(x.getCreationTime())
                        .ISO(x.getPhotoDetails().getISO())
                        .Model(x.getPhotoDetails().getModel())
                        .Description(x.getPhotoDetails().getDescription())
                        .Camera(x.getPhotoDetails().getCamera())
                        .build()).collect(Collectors.toList());
    }

    public List<UserPhotoResponse> getPhotosByUserIdPull(Long userId, Long photoId) throws Exception {

        Optional<Photo> lastPhoto = photoRepository.getLastPhoto(userId, photoId);

        return lastPhoto.map(x -> photoRepository.getUserPhotosLoad(userId, x.getCreationTime(), PageRequest.of(0, 7)).stream()
                .map(y -> UserPhotoResponse.builder()
                        .photoId(y.getId())
                        .photoPath(y.getName())
                        .photoTitle(y.getTitle())
                        .creationTime(y.getCreationTime())
                        .ISO(y.getPhotoDetails().getISO())
                        .Model(y.getPhotoDetails().getModel())
                        .Description(y.getPhotoDetails().getDescription())
                        .Camera(y.getPhotoDetails().getCamera())
                        .build()).collect(Collectors.toList())).orElseThrow(Exception::new);

    }


    public ResponseEntity<Object> uploadImgOnServer(MultipartFile image, Long albumId, String title) throws IOException, URISyntaxException {

        if (!validationService.validationImage(image))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        URL res = getClass().getClassLoader().getResource("static/images/users_photos");
        Path photoPath = Paths.get(res.toURI());
        User user = authenticationService.getCurrentUser();
        String originalFilename = image.getOriginalFilename();
        Optional<Album> optionalAlbum = albumService.findByAlbumIdAndUserId(albumId, user.getId());

        if (optionalAlbum.isPresent()) {
            Photo photo = Photo.builder()
                    .name(image.getOriginalFilename())
                    .title(title)
                    .album(optionalAlbum.get())
                    .build();


            photoDetailsRepository.save(PhotoDetails.builder()
                    .Description("xd")
                    .Model("sf")
                    .ISO(123)
                    .Camera("cds")
                    .photo(photo)
                    .build());

            File photoFile = new File(photoPath + "\\" + user.getUsername() + "-" + photo.getId() + "-" + originalFilename);

            photo.setName(photoFile.getName());
            add(photo);

            try {
                image.transferTo(photoFile);
            } catch (IOException exception) {
                remove(photo);
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    public String handlerFileBuffer(MultipartFile img) throws URISyntaxException, IOException {

        if (!validationService.validationImage(img)) throw new IOException("Wrong type !!!");

        String uuid = UUID.randomUUID().toString();
        URL res = getClass().getClassLoader().getResource("static/images");
        Path photoPath = Paths.get(res.toURI());

        String extension = FilenameUtils.getExtension(img.getOriginalFilename());
        File file = new File(photoPath + "\\" + uuid + '.' + extension);
        img.transferTo(file);
        return uuid;
    }


    public boolean clearBuffer(String uuid) throws URISyntaxException, IOException {

        URL res = getClass().getClassLoader().getResource("static/images");
        Path photoPath = Paths.get(res.toURI());
        return new File(photoPath + "\\" + uuid).delete();

    }


}

