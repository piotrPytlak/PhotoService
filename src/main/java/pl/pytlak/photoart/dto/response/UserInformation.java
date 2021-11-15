package pl.pytlak.photoart.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInformation {

    Long userId;

    Integer userAge;

    String username;

    String firstName;

    String lastName;

    String aboutMe;

    String avatarPath;

    String backgroundPath;

    Long followers;

    Long following;

    Long photoCount;

    Long albumCount;

    Boolean isYourAccount;

}
