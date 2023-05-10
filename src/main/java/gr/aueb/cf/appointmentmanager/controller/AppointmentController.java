package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.model.Patient;
import gr.aueb.cf.appointmentmanager.service.IAppointmentService;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidAppointmentException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidPatientException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    private final IAppointmentService appointmentService;
    private final IDoctorService doctorService;

    public AppointmentController(IAppointmentService appointmentService, IDoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @GetMapping("/create")
    public String showForm(@RequestParam("doctorId") Long doctorId, Model model) throws EntityNotFoundException {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("doctorName", "Dr. " + doctor.getFirstname() + " " + doctor.getLastname());
        return "form";
    }

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
                                    @RequestParam("minute") int minute) throws EntityNotFoundException,
                                    InvalidAppointmentException, InvalidPatientException {

        appointmentService.createAppointment(doctorId,firstname,lastname,phonenumber,ssn,year,month,day,hour,minute);

        return "redirect:/";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("appointmentId") Long appointmentId, Model model) throws EntityNotFoundException {
        model.addAttribute("appointment", appointmentService.getAppointmentById(appointmentId));
        return "update-form";
    }

    @PostMapping("/update")
    public String updateAppointment(@RequestParam("appointmentId") Long appointmentId,
                                    @RequestParam("year") int year,
                                    @RequestParam("month") int month,
                                    @RequestParam("day") int day,
                                    @RequestParam("hour") int hour,
                                    @RequestParam("minute") int minute) throws InvalidAppointmentException, EntityNotFoundException {

        appointmentService.updateAppointment(appointmentId,year,month,day,hour,minute);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam("doctorId") Long doctorId, Model model) throws EntityNotFoundException {
        model.addAttribute("appointment", appointmentService.getAppointmentsByDoctor(doctorId));
        return "appointments";
    }

    @PostMapping("/delete")
    public String deleteAppointment(@RequestParam("firstname") String firstname,@RequestParam("lastname") String lastname)
            throws EntityNotFoundException {

        appointmentService.deleteAppointment(firstname, lastname);

        return "redirect:/";
    }

    @GetMapping("/schedule")
    public String showScheduleForm(@RequestParam("doctorId") Long doctorId, Model model) throws EntityNotFoundException {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctor.getId());
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        return "appointments";
    }
}
