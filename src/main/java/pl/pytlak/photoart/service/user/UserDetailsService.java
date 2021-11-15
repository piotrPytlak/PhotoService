package pl.pytlak.photoart.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.dto.response.UserInformation;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.entity.UserDetails;
import pl.pytlak.photoart.repository.PhotoRepository;
import pl.pytlak.photoart.repository.UserDetailsRepository;
import pl.pytlak.photoart.repository.UserRepository;
import pl.pytlak.photoart.service.Validation.ValidationService;
import pl.pytlak.photoart.service.authentication.AuthenticationService;
import pl.pytlak.photoart.service.photo.PhotoService;
import pl.pytlak.photoart.type.UserDetailsImg;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UserDetailsService {


    private final ValidationService validationService;
    private final PhotoRepository photoRepository;
    private final AuthenticationService authenticationService;
    private final PhotoService photoService;
    private final UserDetailsRepository userDetailsRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    private void worker(Runnable runIfAvatar, Runnable runIfBackGround, UserDetailsImg detailsImg) {
        if (detailsImg == UserDetailsImg.AVATAR)
            runIfAvatar.run();
        else
            runIfBackGround.run();

    }

    @Transactional
    public ResponseEntity<?> uploadUserDetailsImg(MultipartFile img, UserDetailsImg detailsImg) throws IOException, URISyntaxException {

        if (!validationService.validationImage(img))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        String path;

        if (detailsImg == UserDetailsImg.AVATAR)
            path = "static/images/avatars";
        else
            path = "static/images/backgrounds";


        URL res = getClass().getClassLoader().getResource(path);

        Path photoPath = Paths.get(res.toURI());
        User user = authenticationService.getCurrentUser();

        Photo photo = photoRepository.save(Photo.builder()
                .name(img.getOriginalFilename())
                .build());

        File photoFile = new File(photoPath + "\\" + user.getUsername() + "-" + detailsImg.name() + "-" + photo.getId() + "-" + img.getOriginalFilename());
        photo.setName(photoFile.getName());
        photoRepository.save(photo);


        userDetailsRepository.findById(user.getId()).ifPresentOrElse(x -> {
                    Photo photoToRemove = detailsImg == UserDetailsImg.AVATAR ? x.getAvatarPhoto() : x.getBackgroundPhoto();
                    if (photoToRemove != null) {
                        File file = new File(photoToRemove.getName());
                        if (file.delete()) {
                            photoRepository.delete(photoToRemove);
                            worker(() -> x.setAvatarPhoto(photo), () -> x.setBackgroundPhoto(photo), detailsImg);
                            userDetailsRepository.save(x);
                        } else {
                            photoFile.delete();
                        }

                    } else {
                        worker(() -> x.setAvatarPhoto(photo), () -> x.setBackgroundPhoto(photo), detailsImg);
                        userDetailsRepository.save(x);
                    }
                }, () ->

                {

                    UserDetails currentUserDetails = detailsImg == UserDetailsImg.AVATAR ?
                            UserDetails.builder()
                                    .id(user.getId())
                                    .user(user)
                                    .avatarPhoto(photo)
                                    .build()
                            :
                            UserDetails.builder()
                                    .id(user.getId())
                                    .user(user)
                                    .backgroundPhoto(photo)
                                    .build();

                    userDetailsRepository.save(currentUserDetails);
                }
        );

        try {
            img.transferTo(photoFile);
        } catch (
                IOException exception) {
            photoService.remove(photo);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


    public ResponseEntity<UserInformation> getUserInformation(Long userId) {
        Long currentUserId = authenticationService.isAuthenticated() ? authenticationService.getCurrentUser().getId() : null;

        UserInformation userInformation = userRepository.getAllUserInformation(userId)
                .map(x -> UserInformation.builder()
                        .userId(x.getUserId())
                        .aboutMe(x.getAboutMe())
                        .albumCount(x.getAlbumCount())
                        .avatarPath(x.getAvatarPath())
                        .backgroundPath(x.getBackgroundPath())
                        .firstName(x.getFirstName())
                        .lastName(x.getLastName())
                        .userAge(x.getUserAge())
                        .followers(x.getFollowers())
                        .following(x.getFollowing())
                        .photoCount(x.getPhotoCount())
                        .albumCount(x.getAlbumCount())
                        .username(x.getUsername())
                        .isYourAccount(currentUserId == x.getUserId())
                        .build()).orElse(null);
        return userInformation != null ? new ResponseEntity<>(userInformation, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<UserInformation> getCurrentUser() {
        Long currentUserId = authenticationService.getCurrentUser().getId();

        return getUserInformation(currentUserId);

    }
}

