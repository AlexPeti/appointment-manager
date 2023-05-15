package gr.aueb.cf.appointmentmanager.repository;

import gr.aueb.cf.appointmentmanager.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findDoctorById(Long id);
    Optional<Doctor> findByLastname(String lastname);
}
