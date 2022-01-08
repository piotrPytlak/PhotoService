package pl.pytlak.photoart.dto.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddCommentResponse {

    Long userId;

    Long commentId;

    String date;

    String commentText;

    String username;

    String firstName;

    String lastName;

    String avatarPath;
}
