package pl.pytlak.photoart.service.photo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.Exception.NoContentException;
import pl.pytlak.photoart.dto.request.AddPhotoRequest;
import pl.pytlak.photoart.dto.response.CategoryAvgPhotoRateResponse;
import pl.pytlak.photoart.dto.response.UserPhotoResponse;
import pl.pytlak.photoart.entitiyKey.TagPhotoId;
import pl.pytlak.photoart.entity.*;
import pl.pytlak.photoart.repository.*;
import pl.pytlak.photoart.service.Validation.ValidationService;
import pl.pytlak.photoart.service.album.AlbumService;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
                    .camera(addPhotoRequest.getCamera())
                    .ISO(addPhotoRequest.getIso() != null ? addPhotoRequest.getIso().toString() : null)
                    .exif(addPhotoRequest.getExif().replaceAll("\u0000", ""))
                    .model(addPhotoRequest.getModel())
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

    public List<UserPhotoResponse> getAllPhotosByUserId(Long userId) {
        return photoRepository.getAllUserPhotos(userId).stream()
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


    public CategoryAvgPhotoRateResponse getPhotoInformationByPhotoId(Long photoId) throws NoContentException {

        return photoRepository.getPhotoInformationByPhotoId(photoId).map(x -> CategoryAvgPhotoRateResponse.builder()
                .describe(x.getDescribe())
                .title(x.getTitle())
                .camera(x.getCamera())
                .model(x.getModel())
                .ISO(x.getISO())
                .photoId(x.getPhotoId())
                .creativityAvg(x.getCreativityCOUNT() > 0 ? x.getCreativitySUM() / x.getCreativityCOUNT() : null)
                .lightingAvg(x.getLightingCOUNT() > 0 ? x.getLightingSUM() / x.getLightingCOUNT() : null)
                .originalityAvg(x.getOriginalityCOUNT() > 0 ? x.getOriginalitySUM() / x.getOriginalityCOUNT() : null)
                .qualityAvg(x.getQualityCOUNT() > 0 ? x.getQualitySUM() / x.getQualityCOUNT() : null)
                .artisticImpressionsAvg(x.getArtisticImpressionsCOUNT() > 0 ? x.getArtisticImpressionsSUM() / x.getArtisticImpressionsCOUNT() : null)
                .build()).orElseThrow(() -> new NoContentException("Photo not exists !!!"));

    }

    public List<UserPhotoResponse> getPhotosByUserIdAndAlbumId(Long userId, Long albumId) {

        return photoRepository.getPhotosFromAlbum(userId, albumId).stream()
                .map(photo -> UserPhotoResponse.builder()
                        .photoPath(photo.getName())
                        .photoId(photo.getId())
                        .photoTitle(photo.getTitle())
                        .Camera(photo.getPhotoDetails().getCamera())
                        .ISO(photo.getPhotoDetails().getISO())
                        .creationTime(photo.getCreationTime())
                        .Description(photo.getPhotoDetails().getDescription())
                        .Model(photo.getPhotoDetails().getModel())
                        .build())
                .collect(Collectors.toList());
    }
}

