package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IDoctorService {
    Doctor createDoctor(DoctorDTO doctorDTO);
    Doctor updateDoctor(DoctorDTO doctorDTO) throws EntityNotFoundException;
    void deleteDoctor(Long id) throws EntityNotFoundException;
    Doctor getDoctorByLastname(String lastname) throws EntityNotFoundException;
    Doctor getDoctorById(Long id) throws EntityNotFoundException;
    List<Doctor> getAllDoctors() throws EntityNotFoundException;
}
