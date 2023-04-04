package peaksoft.dto.response;

import lombok.*;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.time.Year;

/**
 * name : kutman
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private Long id;
    private String fullName;
    private int dateOfBirth;
    private String email;
    private String phoneNumber;
    private Role role;

    public UserResponse(Long id, String fullName, String email, String phoneNumber, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

}
