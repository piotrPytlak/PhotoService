package pl.pytlak.photoart.dto.request;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pl.pytlak.photoart.validator.ValidAvatar;

@Data
public class AvatarImage {

    @ValidAvatar
    MultipartFile files;
}
