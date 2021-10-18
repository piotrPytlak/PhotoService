package pl.pytlak.photoart.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import pl.pytlak.photoart.type.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Base64;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @Email
    @NotNull
    String email;

    @NotNull
    String username;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotBlank
    @Getter(AccessLevel.NONE)
    String password;

    @NotNull
    Integer age;

    @Getter(AccessLevel.NONE)
    String gender;


    public String getPassword() {
        return new String(Base64.getDecoder().decode(password));
    }

    public Gender getGender() {
        return gender.equals("female") ? Gender.FEMALE : Gender.MALE;
    }

}
