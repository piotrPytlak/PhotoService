package pl.pytlak.photoart.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.entity.UserDetails;
import pl.pytlak.photoart.repository.PhotoRepository;
import pl.pytlak.photoart.repository.UserDetailsRepository;
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


    private void worker(Runnable runIfAvatar, Runnable runIfBackGround, UserDetailsImg detailsImg) {
        if (detailsImg == UserDetailsImg.AVATAR)
            runIfAvatar.run();
        else
            runIfBackGround.run();

    }

    @Transactional
    public ResponseEntity<Object> uploadUserDetailsImg(MultipartFile img, UserDetailsImg detailsImg) throws IOException, URISyntaxException {

        if (!validationService.validationImage(img))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        URL res = getClass().getClassLoader().getResource("static/images");
        Path photoPath = Paths.get(res.toURI());
        User user = authenticationService.getCurrentUser();

        Photo photo = photoRepository.save(Photo.builder()
                .name(img.getOriginalFilename())
                .build());

        File photoFile = new File(photoPath + "\\" + user.getUsername() + "-" + detailsImg.name() + "-" + photo.getId() + "-" + img.getOriginalFilename());
        photo.setName(photoFile.getAbsolutePath());
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
        },()->

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

        try

    {
        img.transferTo(photoFile);
    } catch(
    IOException exception)

    {
        photoService.remove(photo);
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
        return new ResponseEntity<>(HttpStatus.OK);

}


}
