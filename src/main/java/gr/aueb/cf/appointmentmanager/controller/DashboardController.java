package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/doctors/create")
    public String createDoctor(@RequestParam String firstname, @RequestParam String lastname) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setFirstname(firstname);
        doctorDTO.setLastname(lastname);

        doctorService.createDoctor(doctorDTO);

        return "redirect:/dashboard";
    }

}
