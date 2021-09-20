package pl.pytlak.photoart.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.pytlak.photoart.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Value
@RequiredArgsConstructor
public class AuthenticationModel {

    UserModel user;
    List<AlbumModel> albums;

    public AuthenticationModel(User user){
        this.user = new UserModel(user);
        albums = user.getAlbums().stream()
                .map(AlbumModel::new)
                .collect(Collectors.toList());
    }

}
