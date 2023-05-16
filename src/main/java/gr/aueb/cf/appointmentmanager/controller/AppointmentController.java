package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.service.IAppointmentService;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidAppointmentException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidPatientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * AppointmentController handles HTTP requests related to appointments, such as creating, updating,
 * and deleting appointments, as well as displaying appointments for a particular doctor.
 */
@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private final IAppointmentService appointmentService;
    private final IDoctorService doctorService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService, IDoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    /**
     * Displays a form for creating a new {@link Appointment} for the specified {@link Doctor}.
     *
     * @param doctorId the ID of the {@link Doctor} to create an appointment for
     * @param model the {@link Model} to use for adding attributes
     * @return the name of the HTML template to use for rendering the form
     * @throws EntityNotFoundException if the specified {@link Doctor} does not exist
     */
    @GetMapping("/create")
    public String showForm(@RequestParam("doctorId") Long doctorId, Model model) throws EntityNotFoundException {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("doctorName", "Dr. " + doctor.getFirstname() + " " + doctor.getLastname());
        return "form";
    }

    /**
     * Creates a new {@link Appointment} with the specified details and displays
     * the appointment details on a modal window.
     *
     * @param doctorId the ID of the {@link Doctor} for the appointment
     * @param firstname the first name of the patient for the appointment
     * @param lastname the last name of the patient for the appointment
     * @param phonenumber the phone number of the patient for the appointment
     * @param ssn the SSN of the patient for the appointment
     * @param year the year of the appointment date
     * @param month the month of the appointment date
     * @param day the day of the appointment date
     * @param hour the hour of the appointment time
     * @param minute the minute of the appointment time
     * @return the appointment details view if the appointment is successfully created
     * @throws EntityNotFoundException if the specified {@link Doctor} does not exist
     * @throws InvalidAppointmentException if the specified appointment is invalid
     * @throws InvalidPatientException if the specified patient is invalid
     */
    @PostMapping("/create")
    public String createAppointment(@RequestParam("doctorId") Long doctorId,
                                    @RequestParam("firstname") String firstname,
                                    @RequestParam("lastname") String lastname,
                                    @RequestParam("phonenumber") String phonenumber,
                                    @RequestParam("ssn") String ssn,
                                    @RequestParam("year") int year,
                                    @RequestParam("month") int month,
                                    @RequestParam("day") int day,
                                    @RequestParam("hour") int hour,
                                    @RequestParam("minute") int minute,
                                    Model model) throws EntityNotFoundException,
                                    InvalidAppointmentException, InvalidPatientException {

        Appointment appointment = appointmentService.createAppointment(doctorId,firstname,lastname,
                                                        phonenumber,ssn,year,month,day,hour,minute);

        // Displaying the appointment details in the modal after creating it and also formatting the date and time
        String message = "A new appointment for " + appointment.getPatient().getFirstname()
                + " " + appointment.getPatient().getLastname() + " with Dr. " + appointment.getDoctor().getFirstname()
                + " " + appointment.getDoctor().getLastname() + " is booked for " + appointment.getAppointmentDateTime()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ".";

        model.addAttribute("message", message);

        return "appointment-details";
    }

    /**
     * Displays a form for updating an existing {@link Appointment}.
     *
     * @param appointmentId the ID of the {@link Appointment} to update
     * @param model the {@link Model} to use for adding attributes
     * @return the name of the HTML template to use for rendering the form
     * @throws EntityNotFoundException if the specified {@link Appointment} does not exist
     */
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("appointmentId") Long appointmentId, Model model) throws EntityNotFoundException {
        model.addAttribute("appointment", appointmentService.getAppointmentById(appointmentId));
        return "update-form";
    }

    /**
     * Updates an existing appointment with the specified ID to the given date and time and
     * displays the appointment details on a modal window.
     *
     * @param appointmentId The ID of the appointment to update
     * @param year The year of the new appointment date
     * @param month The month of the new appointment date
     * @param day The day of the new appointment date
     * @param hour The hour of the new appointment time
     * @param minute The minute of the new appointment time
     * @throws InvalidAppointmentException if the given date or time is invalid
     * @throws EntityNotFoundException if the appointment with the given ID does not exist
     * @return the appointment details view if the appointment is successfully updated
     */
    @PostMapping("/update")
    public String updateAppointment(@RequestParam("appointmentId") Long appointmentId,
                                    @RequestParam("year") int year,
                                    @RequestParam("month") int month,
                                    @RequestParam("day") int day,
                                    @RequestParam("hour") int hour,
                                    @RequestParam("minute") int minute,
                                    Model model) throws InvalidAppointmentException, EntityNotFoundException {

        Appointment appointment = appointmentService.updateAppointment(appointmentId,year,month,day,hour,minute);

        // Displaying the appointment details in the modal after updating it and also formatting the date and time
        String message = "Updated appointment for patient " + appointment.getPatient().getFirstname() + " " +
                appointment.getPatient().getLastname() + " with Dr. " + appointment.getDoctor().getFirstname() + " "
                + appointment.getDoctor().getLastname() + ". The new date and time is " + appointment.getAppointmentDateTime()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ".";

        model.addAttribute("message", message);

        return "appointment-details";
    }

    /**
     * Deletes the appointment for the specified patient.
     *
     * @param appointmentId the id of the appointment to delete
     * @param doctorId the id of the doctor associated with the appointment
     * @throws EntityNotFoundException if the appointment for the given patient does not exist
     * @return A redirect to the schedule page for the corresponding doctor if the appointment is successfully deleted
     */
    @PostMapping("/delete")
    public String deleteAppointment(@RequestParam("appointmentId") Long appointmentId,@RequestParam("doctorId") Long doctorId) throws EntityNotFoundException {
        appointmentService.deleteAppointmentById(appointmentId);
        // We take the doctor's id from thymeleaf, so we can use it to display his schedule page after deleting an appointment
        return "redirect:/appointment/schedule?doctorId=" + doctorId;
    }

    /**
     * Displays the appointment schedule for the specified doctor.
     *
     * @param doctorId The ID of the doctor to display the schedule for
     * @param model The model to be used for rendering the view
     * @throws EntityNotFoundException if the doctor with the given ID does not exist
     * @return A string representing the name of the view to display
     */
    @GetMapping("/schedule")
    public String showScheduleForm(@RequestParam("doctorId") Long doctorId, Model model) throws EntityNotFoundException {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctor.getId());
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "appointments";
    }
}