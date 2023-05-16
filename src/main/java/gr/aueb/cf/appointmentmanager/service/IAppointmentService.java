package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidAppointmentException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidPatientException;

import java.util.List;

/**
 * Service interface for managing appointments.
 */
public interface IAppointmentService {

    /**
     * Create a new appointment.
     *
     * @param doctorId   the ID of the doctor for the appointment
     * @param firstname  the first name of the patient
     * @param lastname   the last name of the patient
     * @param phonenumber the phone number of the patient
     * @param ssn        the social security number of the patient
     * @param year       the year of the appointment date
     * @param month      the month of the appointment date
     * @param day        the day of the appointment date
     * @param hour       the hour of the appointment time
     * @param minute     the minute of the appointment time
     * @return the created appointment entity
     * @throws EntityNotFoundException      if the doctor or patient with the specified ID is not found
     * @throws InvalidAppointmentException  if the appointment time is outside of office hours or conflicts with an existing appointment
     * @throws InvalidPatientException      if there are errors in the patient details
     */
    Appointment createAppointment(Long doctorId, String firstname, String lastname, String phonenumber,
                                  String ssn, int year, int month, int day, int hour, int minute)
            throws EntityNotFoundException, InvalidAppointmentException, InvalidPatientException;

    /**
     * Update an existing appointment.
     *
     * @param doctorId the ID of the doctor for the appointment
     * @param year          the year of the updated appointment date
     * @param month         the month of the updated appointment date
     * @param day           the day of the updated appointment date
     * @param hour          the hour of the updated appointment time
     * @param minute        the minute of the updated appointment time
     * @return the updated appointment entity
     * @throws EntityNotFoundException     if the appointment with the specified ID is not found
     * @throws InvalidAppointmentException if the updated appointment time is outside of office hours or conflicts with an existing appointment
     */
    Appointment updateAppointment(Long doctorId, int year,
                                  int month, int day, int hour, int minute) throws EntityNotFoundException, InvalidAppointmentException;

    /**
     * Delete an appointment by patient name.
     *
     * @param firstname the first name of the patient
     * @param lastname  the last name of the patient
     * @throws EntityNotFoundException if the appointment with the specified patient name is not found
     */
    void deleteAppointment(String firstname, String lastname) throws EntityNotFoundException;

    /**
     * Delete an appointment by id.
     *
     * @param id the appointment id
     * @throws EntityNotFoundException if the appointment with the specified id is not found
     */
    void deleteAppointmentById(Long id) throws EntityNotFoundException;

    /**
     * Get an appointment by ID.
     *
     * @param id the ID of the appointment
     * @return the appointment entity with the specified ID
     * @throws EntityNotFoundException if the appointment with the specified ID is not found
     */
    Appointment getAppointmentById(Long id) throws EntityNotFoundException;

    /**
     * Get all appointments.
     *
     * @return a list of all appointments
     * @throws EntityNotFoundException if no appointments are found
     */
    List<Appointment> getAllAppointments() throws EntityNotFoundException;

    /**
     * Get all appointments for a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @return a list of appointments for the specified doctor
     * @throws EntityNotFoundException if the doctor with the specified ID is not found or if no appointments are found for the doctor
     */
    List<Appointment> getAppointmentsByDoctor(Long doctorId) throws EntityNotFoundException;

    /**
     * Get all appointments for a specific patient.
     *
     * @param patientId the ID of the patient
     * @return a list of appointments for the specified patient
     * @throws EntityNotFoundException if the patient with the specified ID is not found or if no appointments are found for the patient
     */
    List<Appointment> getAppointmentsByPatient(Long patientId) throws EntityNotFoundException;

    /**
     * Get an appointment by patient first name and last name.
     *
     * @param firstname the first name of the patient
     * @param lastname  the last name of the patient
     * @return the appointment entity for the specified patient
     * @throws EntityNotFoundException if the appointment with the specified patient first name and last name is not found
     */
    Appointment getAppointmentByPatientFirstnameAndLastname(String firstname, String lastname) throws EntityNotFoundException;
}
