package gr.aueb.cf.appointmentmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "APPOINTMENTS")
public class Appointment {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    @Column(name = "DATE")
    private LocalDateTime appointmentDateTime;

    public Appointment(Long id, Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
    }
}
