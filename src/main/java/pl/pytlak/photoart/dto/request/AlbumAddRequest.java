package pl.pytlak.photoart.dto.request;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumAddRequest {

    @NotNull
    String name;

    String description;


}
