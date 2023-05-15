package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.PatientDTO;
import gr.aueb.cf.appointmentmanager.model.Patient;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Service interface for managing patients.
 */
public interface IPatientService {

    /**
     * Create a new patient.
     *
     * @param patientDTO the patientDTO object containing the patient details
     * @return the created patient entity
     */
    Patient createPatient(PatientDTO patientDTO);

    /**
     * Update an existing patient.
     *
     * @param patientDTO the patientDTO object containing the updated patient details
     * @return the updated patient entity
     * @throws EntityNotFoundException if the patient with the specified ID is not found
     */
    Patient updatePatient(PatientDTO patientDTO) throws EntityNotFoundException;

    /**
     * Delete a patient by ID.
     *
     * @param id the ID of the patient to delete
     * @throws EntityNotFoundException if the patient with the specified ID is not found
     */
    void deletePatient(Long id) throws EntityNotFoundException;

    /**
     * Get a patient by their full name.
     *
     * @param firstname the first name of the patient
     * @param lastname  the last name of the patient
     * @return the patient entity with the specified full name
     * @throws EntityNotFoundException if the patient with the specified full name is not found
     */
    Patient getPatientByFullname(String firstname, String lastname) throws EntityNotFoundException;

    /**
     * Get a patient by their ID.
     *
     * @param id the ID of the patient
     * @return the patient entity with the specified ID
     * @throws EntityNotFoundException if the patient with the specified ID is not found
     */
    Patient getPatientById(Long id) throws EntityNotFoundException;

    /**
     * Get all patients.
     *
     * @return a list of all patients
     * @throws EntityNotFoundException if no patients are found
     */
    List<Patient> getAllPatients() throws EntityNotFoundException;
}
