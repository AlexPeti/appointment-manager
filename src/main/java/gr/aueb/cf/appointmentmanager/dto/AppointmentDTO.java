package gr.aueb.cf.appointmentmanager.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AppointmentDTO {

    private Long id;

    private DoctorDTO doctorDTO;

    private PatientDTO patientDTO;

    private LocalDateTime appointmentDateTime;

    public AppointmentDTO(Long id, DoctorDTO doctorDTO, PatientDTO patientDTO, LocalDateTime appointmentDateTime) {
        this.id = id;
        this.doctorDTO = doctorDTO;
        this.patientDTO = patientDTO;
        this.appointmentDateTime = appointmentDateTime;
    }
}
