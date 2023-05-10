package gr.aueb.cf.appointmentmanager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PatientDTO {

    private Long id;

    @NotEmpty(message = "Field first name is required")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "First name cannot contain numbers")
    private String firstname;

    @NotEmpty(message = "Field last name is required")
    @Pattern(regexp = "^[^\\d\\s]+$", message = "Last name cannot contain numbers")
    private String lastname;

    @Size(min = 8, max = 8, message = "Phone number must be exactly 10 characters")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number can only contain digits")
    @NotEmpty(message = "Field phone number is required")
    private String phoneNumber;

    @Size(min = 11, max = 11, message = "SSN must be exactly 11 characters")
    @Pattern(regexp = "^[0-9]*$", message = "SSN can only contain digits")
    @NotEmpty(message = "Field SSN is required")
    private String ssn;

    public PatientDTO(Long id, String firstname, String lastname, String phoneNumber, String ssn) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
    }
}
