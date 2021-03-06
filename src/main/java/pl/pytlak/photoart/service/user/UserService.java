package pl.pytlak.photoart.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.dto.request.AboutMeRequest;
import pl.pytlak.photoart.dto.request.AddPhotoRequest;
import pl.pytlak.photoart.dto.response.SearchUserResponse;
import pl.pytlak.photoart.dto.response.UserInformation;
import pl.pytlak.photoart.entitiyKey.FollowId;
import pl.pytlak.photoart.entity.Follow;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.entity.UserDetails;
import pl.pytlak.photoart.repository.FollowRepository;
import pl.pytlak.photoart.repository.UserDetailsRepository;
import pl.pytlak.photoart.repository.UserRepository;
import pl.pytlak.photoart.security.UserDetailsImpl;
import pl.pytlak.photoart.service.authentication.AuthenticationService;
import pl.pytlak.photoart.service.photo.PhotoService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final AuthenticationService authenticationService;
    private final FollowRepository followRepository;
    private final PhotoService photoService;

    public void makeFollow(Long userId) throws IllegalArgumentException {
        User user = authenticationService.getCurrentUser();


        if (user.getId().equals(userId))
            throw new IllegalArgumentException("You cant follow yourself");

        FollowId followId = new FollowId(user.getId(), userId);

        User followerUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not found!"));


        userRepository.findUserById(userId).ifPresent(x -> {
            Follow follow = Follow.builder()
                    .followId(followId)
                    .user(user)
                    .followerUser(followerUser)
                    .build();

            followRepository.save(follow);
        });


    }


    public List<SearchUserResponse> searchUsers(String searchParam) {

        return userRepository.searchByUsername(searchParam, PageRequest.of(0, 15)).stream()
                .map(x -> SearchUserResponse.builder()
                        .userId((Long) x[3])
                        .username((String) x[0])
                        .followers((long) x[2])
                        .avatarImgPath((String) x[1])
                        .build())
                .collect(Collectors.toList());


    }


    public UserInformation editAboutMe(AboutMeRequest aboutMeRequest){
        User loggedUser = authenticationService.getCurrentUser();

        loggedUser.getUserDetails().setAboutMe(aboutMeRequest.getContent());

        UserDetails userDetails = userDetailsRepository.save(loggedUser.getUserDetails());

        return UserInformation.builder()
                .aboutMe(userDetails.getAboutMe())
                .build();

    }


    public void addPhoto(AddPhotoRequest addPhotoRequest) {

        User currentUser = authenticationService.getCurrentUser();

        photoService.addPhoto(addPhotoRequest, currentUser);

    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }


}
