package gr.aueb.cf.appointmentmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "PATIENTS")
public class Patient {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "FIRSTNAME")
    private String lastname;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "SSN")
    private String ssn;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    public Patient(Long id, String firstname, String lastname, String phoneNumber, String ssn, List<Appointment> appointments) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.ssn = ssn;
        this.appointments = appointments;
    }
}