package pl.pytlak.photoart.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserResponse {

    Long userId;
    String username;
    String avatarImgPath;
    Long followers;

    public SearchUserResponse(Long userId, String username, String avatarImgPath, Long followers) {
        this.userId = userId;
        this.username = username;
        this.avatarImgPath = avatarImgPath;
        this.followers = followers;
    }

    public SearchUserResponse() {
    }
}
