package pl.pytlak.photoart.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pl.pytlak.photoart.type.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    String password;

    @NotNull
    Integer age;

    @NotNull
    Gender gender;

}
