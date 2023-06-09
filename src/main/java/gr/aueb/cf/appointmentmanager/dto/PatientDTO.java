package gr.aueb.cf.appointmentmanager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String phoneNumber;

    private String ssn;

    public PatientDTO(Long id, String firstname, String lastname, String phoneNumber, String ssn) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
    }
}
