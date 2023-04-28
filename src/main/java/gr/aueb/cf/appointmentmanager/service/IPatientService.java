package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.PatientDTO;
import gr.aueb.cf.appointmentmanager.model.Patient;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IPatientService {
    Patient createPatient(PatientDTO patientDTO);
    Patient updatePatient(PatientDTO patientDTO) throws EntityNotFoundException;
    void deletePatient(Long id) throws EntityNotFoundException;
    Patient getPatientByFullname(String firstname, String lastname) throws EntityNotFoundException;
    Patient getPatientById(Long id) throws EntityNotFoundException;
    List<Patient> getAllPatients() throws EntityNotFoundException;
}
