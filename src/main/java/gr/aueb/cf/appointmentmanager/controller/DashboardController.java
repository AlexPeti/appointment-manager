package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The controller for managing the dashboard page and doctor creation.
 */
@Controller
public class DashboardController {

    private final IDoctorService doctorService;

    @Autowired
    public DashboardController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Displays the dashboard page, listing all the available doctors.
     *
     * @param model the {@link Model} to use for adding attributes
     * @return the name of the HTML template to use for rendering the page
     * @throws EntityNotFoundException if there is an error retrieving the doctors from the database
     */
    @GetMapping("/dashboard")
    public String getDashboard(Model model) throws EntityNotFoundException {
        List<Doctor> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "dashboard";
    }

    /**
     * Creates a new doctor with the given first name and last name.
     *
     * @param firstname the first name of the new doctor
     * @param lastname the last name of the new doctor
     * @return A redirect to the dashboard page if the doctor is successfully created
     */
    @PostMapping("/doctors/create")
    public String createDoctor(@RequestParam String firstname, @RequestParam String lastname) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setFirstname(firstname);
        doctorDTO.setLastname(lastname);

        doctorService.createDoctor(doctorDTO);

        return "redirect:/dashboard";
    }

}
