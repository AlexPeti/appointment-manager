package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.PatientDTO;
import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.model.Patient;
import gr.aueb.cf.appointmentmanager.repository.AppointmentRepository;
import gr.aueb.cf.appointmentmanager.repository.DoctorRepository;
import gr.aueb.cf.appointmentmanager.repository.PatientRepository;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidAppointmentException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidPatientException;
import gr.aueb.cf.appointmentmanager.validator.PatientValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PatientValidator patientValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  DoctorRepository doctorRepository,
                                  PatientRepository patientRepository,
                                  PatientValidator patientValidator, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.patientValidator = patientValidator;
        this.modelMapper = modelMapper;
    }

    /**
     * Helper method used inside the "createAppointment" method for creating the date and time for the new appointment.
     * Converts the provided year, month, day, hour, and minute into a LocalDateTime object and validates if it falls within the office hours.
     * This method helps to reduce the size and complexity of the "createAppointment" method by extracting the logic for creating and validating
     * the appointment date and time.
     *
     * @param year   the year of the appointment
     * @param month  the month of the appointment
     * @param day    the day of the appointment
     * @param hour   the hour of the appointment
     * @param minute the minute of the appointment
     * @return the LocalDateTime object representing the appointment date and time
     * @throws InvalidAppointmentException if the appointment time is outside of the office hours or if the hour value is invalid
     */
    private LocalDateTime getLocalDateTimeAndWorkingHours(int year, int month, int day, int hour, int minute) throws InvalidAppointmentException {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);

        LocalDateTime officeOpeningTime = dateTime.withHour(9).withMinute(0);
        LocalDateTime officeClosingTime = dateTime.withHour(21).withMinute(0);

        if (dateTime.isBefore(officeOpeningTime) || dateTime.isAfter(officeClosingTime)) {
            throw new InvalidAppointmentException("Appointment time is outside of office hours.");
        }

        if (dateTime.getHour() < 9 || dateTime.getHour() > 20 || (dateTime.getHour() == 20 && dateTime.getMinute() > 50)) {
            throw new InvalidAppointmentException("Invalid hour value for appointment time.");
        }

        return dateTime;
    }

    /**
     * Helper method used inside the "createAppointment" method for creating a new appointment.
     * Creates a new appointment for the specified doctor, patient, and date and time.
     * Checks for any existing appointments with the same date and time before booking.
     * This method helps to reduce the size and complexity of the "createAppointment" method by
     * extracting the logic for creating a new appointment and checking for conflicts.
     *
     * @param doctor   the doctor for the appointment
     * @param patient  the patient for the appointment
     * @param dateTime the date and time of the appointment
     * @return the created appointment
     * @throws InvalidAppointmentException if the doctor already has an appointment at the same date and time
     */
    private Appointment createNewAppointment(Doctor doctor, Patient patient, LocalDateTime dateTime) throws InvalidAppointmentException {
        // Check for appointments with the same date and time before booking
        List<Appointment> doctorAppointments = doctor.getAppointments();
        for (Appointment appointment : doctorAppointments) {
            LocalDateTime existingDateTime = appointment.getAppointmentDateTime();
            if (existingDateTime.getYear() == dateTime.getYear() &&
                    existingDateTime.getMonth().getValue() == dateTime.getMonth().getValue() &&
                    existingDateTime.getDayOfMonth() == dateTime.getDayOfMonth() &&
                    existingDateTime.getHour() == dateTime.getHour() &&
                    existingDateTime.getMinute() == dateTime.getMinute()) {
                throw new InvalidAppointmentException("Doctor already has an appointment at that time.");
            }
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(dateTime);
        appointmentRepository.save(appointment);

        return appointment;
    }

    @Transactional
    @Override
    public Appointment createAppointment(Long doctorId, String firstname, String lastname,
                                         String phonenumber, String ssn, int year, int month,
                                         int day, int hour, int minute) throws EntityNotFoundException,
                                            InvalidAppointmentException, InvalidPatientException {

        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if (doctor == null) {
            throw new EntityNotFoundException(Doctor.class, doctorId);
        }

        // Check if patient exists by SSN
        Patient patient = patientRepository.findPatientBySsn(ssn);
        if (patient == null) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setFirstname(firstname);
            patientDTO.setLastname(lastname);
            patientDTO.setPhoneNumber(phonenumber);
            patientDTO.setSsn(ssn);

            // Validate the patientDTO
            Errors errors = new BeanPropertyBindingResult(patientDTO, "patientDTO");
            patientValidator.validate(patientDTO, errors);
            if (errors.hasErrors()) {
                throw new InvalidPatientException("Error in required fields");
            }

            patient = modelMapper.map(patientDTO, Patient.class);
            patient = patientRepository.save(patient);
        }

        // Construct the date and time of the appointment by using a helper method that handles all logic and checks
        LocalDateTime dateTime = getLocalDateTimeAndWorkingHours(year,month,day,hour,minute);

        // Construct and create the new appointment by using a helper method that handles all logic and checks
        return createNewAppointment(doctor, patient, dateTime);
    }

    @Transactional
    @Override
    public Appointment updateAppointment(Long appointmentId, int year, int month,
                                         int day, int hour, int minute) throws EntityNotFoundException, InvalidAppointmentException {
        Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new EntityNotFoundException(Appointment.class, appointmentId);
        }

        LocalDateTime dateTime = getLocalDateTimeAndWorkingHours(year,month,day,hour,minute);

        appointment.setAppointmentDateTime(dateTime);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Transactional
    @Override
    public void deleteAppointment(String firstname, String lastname) throws EntityNotFoundException {
        Appointment appointment = appointmentRepository.findAppointmentByPatientFirstnameAndPatientLastname(firstname,lastname);

        if (appointment == null) {
            throw new EntityNotFoundException(Appointment.class,0L);
        }

        appointmentRepository.delete(appointment);
    }

    public void deleteAppointmentById(Long id) throws EntityNotFoundException {
        Appointment appointment = appointmentRepository.getAppointmentById(id);

        if (appointment == null) {
            throw new EntityNotFoundException(Appointment.class, 0L);
        }

        appointmentRepository.delete(appointment);
    }

    @Override
    public Appointment getAppointmentById(Long id) throws EntityNotFoundException {
        Appointment appointment = appointmentRepository.getAppointmentById(id);

        if (appointment == null) {
            throw new EntityNotFoundException(Appointment.class,id);
        }
        return appointment;
    }

    @Override
    public List<Appointment> getAllAppointments() throws EntityNotFoundException {
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            throw new EntityNotFoundException(Appointment.class,0L);
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) throws EntityNotFoundException {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if (doctor == null) {
            throw new EntityNotFoundException(Doctor.class, doctorId);
        }
        List<Appointment> appointments = appointmentRepository.findAllByDoctorId(doctorId);
        if (appointments.isEmpty()) {
            throw new EntityNotFoundException(Appointment.class,0L);
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(Long patientId) throws EntityNotFoundException {
        Patient patient = patientRepository.findPatientById(patientId);
        if (patient == null) {
            throw new EntityNotFoundException(Patient.class, patientId);
        }
        List<Appointment> appointments = appointmentRepository.findAllByPatientId(patientId);
        if (appointments.isEmpty()) {
            throw new EntityNotFoundException(Appointment.class,0L);
        }
        return appointments;
    }

    public Appointment getAppointmentByPatientFirstnameAndLastname(String firstname, String lastname) throws EntityNotFoundException {
        Appointment appointment = appointmentRepository.findAppointmentByPatientFirstnameAndPatientLastname(firstname,lastname);

        if (appointment == null) {
            throw new EntityNotFoundException(Appointment.class,0L);
        }
        return appointment;
    }
}
