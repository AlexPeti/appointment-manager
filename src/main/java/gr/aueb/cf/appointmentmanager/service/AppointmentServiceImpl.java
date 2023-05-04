package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.model.Patient;
import gr.aueb.cf.appointmentmanager.repository.AppointmentRepository;
import gr.aueb.cf.appointmentmanager.repository.DoctorRepository;
import gr.aueb.cf.appointmentmanager.repository.PatientRepository;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidAppointmentException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  DoctorRepository doctorRepository,
                                  PatientRepository patientRepository,
                                  ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    @Override
    public Appointment createAppointment(Long doctorId, String firstname, String lastname,
                                         String phonenumber, String ssn, int year, int month,
                                         int day, int hour, int minute) throws EntityNotFoundException, InvalidAppointmentException {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if (doctor == null) {
            throw new EntityNotFoundException(Doctor.class, doctorId);
        }

        Patient patient = patientRepository.findByFirstnameAndLastname(firstname, lastname);
        if (patient == null) {
            Patient newPatient = new Patient();
            newPatient.setFirstname(firstname);
            newPatient.setLastname(lastname);
            newPatient.setPhoneNumber(phonenumber);
            newPatient.setSsn(ssn);
            patient = patientRepository.save(newPatient);
        }

        // Hardcoding a + 2 value to hour temporarily, because the LocalDateTime default is GMT +0, so it changes
        // any value we give it to -2
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour + 2, minute);
        ZoneId zoneId = ZoneId.of("Europe/Athens");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneId);

        LocalDateTime officeOpeningTime = dateTime.withHour(9).withMinute(0);
        LocalDateTime officeClosingTime = dateTime.withHour(21).withMinute(0);

        if (dateTime.isBefore(officeOpeningTime) || dateTime.isAfter(officeClosingTime)) {
            throw new InvalidAppointmentException("Appointment time is outside of office hours.");
        }

        if (dateTime.getHour() < 9 || dateTime.getHour() > 20 || (dateTime.getHour() == 20 && dateTime.getMinute() > 30)) {
            throw new InvalidAppointmentException("Invalid hour value for appointment time.");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(dateTime);
        appointmentRepository.save(appointment);
        return appointment;
    }

//    @Transactional
//    @Override
//    public Appointment updateAppointment(Long appointmentId, int year, int month,
//                                         int day, int hour, int minute) throws EntityNotFoundException, InvalidAppointmentException {
//        Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);
//        if (appointment == null) {
//            throw new EntityNotFoundException(Appointment.class, appointmentId);
//        }
//        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
//        LocalDateTime officeOpeningTime = dateTime.withHour(9).withMinute(0);
//        LocalDateTime officeClosingTime = dateTime.withHour(21).withMinute(0);
//
//        if (dateTime.isBefore(officeOpeningTime) || dateTime.isAfter(officeClosingTime)) {
//            throw new InvalidAppointmentException("Appointment time is outside of office hours.");
//        }
//
//        if (dateTime.getHour() < 9 || dateTime.getHour() > 20 || (dateTime.getHour() == 20 && dateTime.getMinute() > 30)) {
//            throw new InvalidAppointmentException("Invalid hour value for appointment time.");
//        }
//
//        appointment.setAppointmentDateTime(dateTime);
//        appointmentRepository.save(appointment);
//        return appointment;
//    }

    @Transactional
    @Override
    public Appointment updateAppointment(String firstname, String lastname, int year, int month,
                                         int day, int hour, int minute) throws EntityNotFoundException, InvalidAppointmentException {

        Appointment appointment = appointmentRepository.findAppointmentByPatientFirstnameAndPatientLastname(firstname,lastname);
        if (appointment == null) {
            throw new EntityNotFoundException(Appointment.class, 0L);
        }
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour + 2, minute);
        LocalDateTime officeOpeningTime = dateTime.withHour(9).withMinute(0);
        LocalDateTime officeClosingTime = dateTime.withHour(21).withMinute(0);

        if (dateTime.isBefore(officeOpeningTime) || dateTime.isAfter(officeClosingTime)) {
            throw new InvalidAppointmentException("Appointment time is outside of office hours.");
        }

        if (dateTime.getHour() < 9 || dateTime.getHour() > 20 || (dateTime.getHour() == 20 && dateTime.getMinute() > 30)) {
            throw new InvalidAppointmentException("Invalid hour value for appointment time.");
        }
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
}
