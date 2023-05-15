package gr.aueb.cf.appointmentmanager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    @NotEmpty
    private String confirmPassword;

    public UserDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
