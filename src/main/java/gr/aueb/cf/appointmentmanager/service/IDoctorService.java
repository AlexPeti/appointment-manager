package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Service interface for managing doctors.
 */
public interface IDoctorService {

    /**
     * Create a new doctor.
     *
     * @param doctorDTO the doctorDTO object containing the doctor details
     * @return the created doctor entity
     */
    Doctor createDoctor(DoctorDTO doctorDTO);

    /**
     * Update an existing doctor.
     *
     * @param doctorDTO the doctorDTO object containing the updated doctor details
     * @return the updated doctor entity
     * @throws EntityNotFoundException if the doctor with the specified ID is not found
     */
    Doctor updateDoctor(DoctorDTO doctorDTO) throws EntityNotFoundException;

    /**
     * Delete a doctor by ID.
     *
     * @param id the ID of the doctor to delete
     * @throws EntityNotFoundException if the doctor with the specified ID is not found
     */
    void deleteDoctor(Long id) throws EntityNotFoundException;

    /**
     * Get a doctor by their last name.
     *
     * @param lastname the last name of the doctor
     * @return the doctor entity with the specified last name
     * @throws EntityNotFoundException if the doctor with the specified last name is not found
     */
    Doctor getDoctorByLastname(String lastname) throws EntityNotFoundException;

    /**
     * Get a doctor by their ID.
     *
     * @param id the ID of the doctor
     * @return the doctor entity with the specified ID
     * @throws EntityNotFoundException if the doctor with the specified ID is not found
     */
    Doctor getDoctorById(Long id) throws EntityNotFoundException;

    /**
     * Get all doctors.
     *
     * @return a list of all doctors
     * @throws EntityNotFoundException if no doctors are found
     */
    List<Doctor> getAllDoctors() throws EntityNotFoundException;
}
