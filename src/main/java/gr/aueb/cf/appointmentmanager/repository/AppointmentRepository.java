package gr.aueb.cf.appointmentmanager.repository;

import gr.aueb.cf.appointmentmanager.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    void deleteAppointmentById(Long id);

//    List<Appointment> findByDoctorIdOrderByDateAsc(Long id);
//
//    Optional<Appointment> findByIdAndDoctorId(Long appointmentId, Long id);
}
