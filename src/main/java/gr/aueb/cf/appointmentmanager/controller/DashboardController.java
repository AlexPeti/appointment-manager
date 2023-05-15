package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.appointmentmanager.service.exceptions.InvalidDoctorException;
import gr.aueb.cf.appointmentmanager.validator.DoctorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller for managing the dashboard page and doctor creation.
 */
@Controller
public class DashboardController {

    private final IDoctorService doctorService;
    private final DoctorValidator doctorValidator;

    @Autowired
    public DashboardController(IDoctorService doctorService, DoctorValidator doctorValidator) {
        this.doctorService = doctorService;
        this.doctorValidator = doctorValidator;
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
     * Creates a new doctor with the given firstname and lastname
     * and also validates the doctor before creating.
     *
     * @param firstname The firstname of the Doctor
     * @param lastname The lastname of the Doctor
     * @param model The model object for the view
     * @throws InvalidDoctorException If the DoctorDTO is not valid
     * @return A redirect to the dashboard page if the doctor is successfully created
     */
    @PostMapping("/doctors/create")
    public String createDoctor(@RequestParam String firstname, @RequestParam String lastname, Model model) throws InvalidDoctorException {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setFirstname(firstname);
        doctorDTO.setLastname(lastname);
        // Validate the doctorDTO
        Errors errors = new BeanPropertyBindingResult(doctorDTO, "doctorDTO");
        doctorValidator.validate(doctorDTO, errors);
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors());
            throw new InvalidDoctorException("Invalid firstname or lastname");
        }

        doctorService.createDoctor(doctorDTO);

        return "redirect:/dashboard";
    }

    /**
     * Deletes a doctor with the given ID.
     *
     * @param doctorId the ID of the doctor to be deleted
     * @return A redirect to the dashboard page
     */
    @PostMapping("/doctors/delete")
    public String deleteDoctor(@RequestParam("doctorId") Long doctorId) throws EntityNotFoundException {
        doctorService.deleteDoctor(doctorId);
        return "redirect:/dashboard";
    }
}
