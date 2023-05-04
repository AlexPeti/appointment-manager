package gr.aueb.cf.appointmentmanager.repository;

import gr.aueb.cf.appointmentmanager.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment getAppointmentById(Long id);
    Appointment findAppointmentByPatientFirstnameAndPatientLastname(String firstname, String lastname);
    void deleteAppointmentById(Long id);
    List<Appointment> findAllByDoctorId(Long id);
    List<Appointment> findAllByPatientId(Long id);
    List<Appointment> findAll();


}
