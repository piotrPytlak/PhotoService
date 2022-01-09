package pl.pytlak.photoart.validator;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

@RequiredArgsConstructor
public class ValidatorAvatar implements ConstraintValidator<ValidAvatar, MultipartFile> {

    private final Tika apacheTika;


    @Override
    public void initialize(ValidAvatar constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile avatarImage, ConstraintValidatorContext constraintValidatorContext) {
        try {

            return apacheTika.detect(avatarImage.getInputStream()).startsWith("image/") && avatarImage.getSize() <= 35000000L;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
