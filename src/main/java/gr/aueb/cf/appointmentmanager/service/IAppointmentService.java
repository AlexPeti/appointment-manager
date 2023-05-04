package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.dto.PatientDTO;
import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidAppointmentException;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    Appointment createAppointment(Long doctorId, String firstname, String lastname, String phonenumber,
                                  String ssn, int year, int month, int day, int hour, int minute)
                                    throws EntityNotFoundException, InvalidAppointmentException;
    Appointment updateAppointment(String firstname, String lastname, int year,
                                  int month, int day, int hour, int minute) throws EntityNotFoundException, InvalidAppointmentException;
    void deleteAppointment(String firstname, String lastname) throws EntityNotFoundException;
    Appointment getAppointmentById(Long id) throws EntityNotFoundException;
    List<Appointment> getAllAppointments() throws EntityNotFoundException;
    List<Appointment> getAppointmentsByDoctor(Long doctorId) throws EntityNotFoundException;
    List<Appointment> getAppointmentsByPatient(Long patientId) throws EntityNotFoundException;
}
