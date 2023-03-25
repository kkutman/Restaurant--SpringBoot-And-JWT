package peaksoft.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.intellij.lang.annotations.Pattern;
import peaksoft.enums.Role;

import java.time.LocalDate;

/**
 * name : kutman
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @Email
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private int experience;
}
