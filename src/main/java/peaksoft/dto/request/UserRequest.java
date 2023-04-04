package peaksoft.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.intellij.lang.annotations.Pattern;
import peaksoft.annptaion.Password;
import peaksoft.enums.Role;

import java.time.LocalDate;

/**
 * name : kutman
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Password
    private String password;
    @NotBlank
    private String phoneNumber;
    private Role role;
    @NotNull
    private int experience;
}
