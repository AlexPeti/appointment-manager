package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.model.Patient;
import gr.aueb.cf.appointmentmanager.service.IAppointmentService;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidAppointmentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/appointment")
public class BookAppointmentController {

    private final IAppointmentService appointmentService;
    private final IDoctorService doctorService;

    public BookAppointmentController(IAppointmentService appointmentService, IDoctorService doctorService) {
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
                                    @RequestParam("minute") int minute) throws EntityNotFoundException, InvalidAppointmentException {

        Doctor doctor = doctorService.getDoctorById(doctorId);
        Patient patient = new Patient();
        patient.setFirstname(firstname);
        patient.setLastname(lastname);
        patient.setPhoneNumber(phonenumber);
        patient.setSsn(ssn);

        appointmentService.createAppointment(doctorId,firstname,lastname,phonenumber,ssn,year,month,day,hour,minute);

        return "redirect:/";
    }
}
