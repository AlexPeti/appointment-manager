package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final IDoctorService doctorService;

    public DashboardController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) throws EntityNotFoundException {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "dashboard";
    }
}
