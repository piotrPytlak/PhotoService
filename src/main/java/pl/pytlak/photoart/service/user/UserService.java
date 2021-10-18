package pl.pytlak.photoart.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.entitiyKey.FollowId;
import pl.pytlak.photoart.entity.Follow;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.repository.FollowRepository;
import pl.pytlak.photoart.repository.UserRepository;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final FollowRepository followRepository;

    public void makeFollow(Long userId) throws IllegalArgumentException {
        User user = authenticationService.getCurrentUser();

        // 1. Follow yourself

        if (user.getId().equals(userId))
            throw new IllegalArgumentException("You cant follow yourself");

        Consumer<User> createFollow = x -> {
            Follow follow = Follow.builder()
                    .idFollowerUser(x.getId())
                    .idUser(user.getId())
                    .build();

            follow = followRepository.save(follow);
            System.out.println(followRepository.test(follow.getIdFollowerUser(), user.getId()).get().getFollowerUser());
        };


        userRepository.findUserById(userId).ifPresent(createFollow);

    }


}
