package pl.pytlak.photoart.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.pytlak.photoart.entity.User;

@Value
@RequiredArgsConstructor
public class UserModel {

    String name;
    String email;

    public UserModel(User user) {
        name = user.getEmail();
        email = user.getEmail();
    }
}
