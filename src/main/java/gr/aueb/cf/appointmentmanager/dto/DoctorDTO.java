package gr.aueb.cf.appointmentmanager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DoctorDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private List<AppointmentDTO> appointmentsDTO = new ArrayList<>();

    public DoctorDTO(Long id, String firstname, String lastname, List<AppointmentDTO> appointmentsDTO) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.appointmentsDTO = appointmentsDTO;
    }
}
