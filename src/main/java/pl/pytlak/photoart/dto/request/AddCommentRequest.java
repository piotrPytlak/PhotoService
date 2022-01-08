package pl.pytlak.photoart.dto.request;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddCommentRequest {

    @NotNull
    Long photoId;

    @NotNull
    String commentText;

}
