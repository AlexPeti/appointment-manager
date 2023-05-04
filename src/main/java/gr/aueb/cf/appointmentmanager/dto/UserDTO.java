package gr.aueb.cf.appointmentmanager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty
    private String username;

    @Size(min = 8, message = "Password must have at least ${min} characters")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    public UserDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
