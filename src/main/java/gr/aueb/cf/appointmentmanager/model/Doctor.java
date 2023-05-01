package gr.aueb.cf.appointmentmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "DOCTORS")
public class Doctor {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "FIRSTNAME" ,nullable = false)
    private String firstname;

    @Column(name = "LASTNAME" ,nullable = false)
    private String lastname;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor(Long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Adds an appointment to the doctor's list of appointments
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.setDoctor(this);
    }

    // Removes an appointment from the doctor's list of appointments
    public void removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.setDoctor(null);
    }
}
