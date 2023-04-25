package gr.aueb.cf.appointmentmanager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PatientDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String phoneNumber;

    private String ssn;

    private List<AppointmentDTO> appointmentsDTO = new ArrayList<>();

    public PatientDTO(Long id, String firstname, String lastname, String phoneNumber, String ssn, List<AppointmentDTO> appointmentsDTO) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
        this.appointmentsDTO = appointmentsDTO;
    }
}
