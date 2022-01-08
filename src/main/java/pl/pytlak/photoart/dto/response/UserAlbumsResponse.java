package pl.pytlak.photoart.dto.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAlbumsResponse {


    Long albumId;

    String name;

    Timestamp createTime;

    String description;


}
