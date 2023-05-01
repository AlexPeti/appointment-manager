package gr.aueb.cf.appointmentmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    @NotNull
    private Doctor doctor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    @NotNull
    private Patient patient;

    @Column(name = "DATE" ,nullable = false)
    private LocalDateTime appointmentDateTime;

    public Appointment(Long id, Doctor doctor, Patient patient, LocalDateTime appointmentDateTime) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
    }

    public void removeDoctor() {
        if (doctor != null) {
            doctor.removeAppointment(this);
        }
    }

    public void removePatient() {
        if (patient != null) {
            patient.removeAppointment(this);
        }
    }

}
