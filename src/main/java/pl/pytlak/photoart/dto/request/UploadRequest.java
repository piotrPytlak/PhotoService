package pl.pytlak.photoart.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.beans.Transient;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadRequest {

    @NotNull
    String title;

    @NotNull
    Long albumId;

    MultipartFile img;

}
