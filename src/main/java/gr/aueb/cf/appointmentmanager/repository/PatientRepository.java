package gr.aueb.cf.appointmentmanager.repository;

import gr.aueb.cf.appointmentmanager.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByFirstnameAndLastname(String firstname, String lastname);
    Patient findPatientById(Long id);
    Patient findPatientBySsn(String ssn);
}
