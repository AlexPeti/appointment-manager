package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.model.Patient;
import gr.aueb.cf.appointmentmanager.service.IAppointmentService;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidAppointmentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("doctorId") Long doctorId, Model model) throws EntityNotFoundException {
        model.addAttribute("appointment", appointmentService.getAppointmentsByDoctor(doctorId));
        return "update-form";
    }

    @PostMapping("/update")
    public String updateAppointment(@RequestParam("firstname") String firstname,
                                    @RequestParam("lastname") String lastname,
                                    @RequestParam("year") int year,
                                    @RequestParam("month") int month,
                                    @RequestParam("day") int day,
                                    @RequestParam("hour") int hour,
                                    @RequestParam("minute") int minute) throws EntityNotFoundException, InvalidAppointmentException {

        appointmentService.updateAppointment(firstname, lastname, year, month, day, hour, minute);

        return "dashboard";
    }

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam("doctorId") Long doctorId, Model model) throws EntityNotFoundException {
        model.addAttribute("appointment", appointmentService.getAppointmentsByDoctor(doctorId));
        return "delete-form";
    }


    @PostMapping("/delete")
    public String deleteAppointment(@RequestParam("firstname") String firstname,@RequestParam("lastname") String lastname)
            throws EntityNotFoundException {

        appointmentService.deleteAppointment(firstname, lastname);

        return "dashboard";
    }
}
