package gr.aueb.cf.appointmentmanager.validator;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DoctorValidator implements Validator {

    private final IDoctorService doctorService;
    @Autowired
    public DoctorValidator(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return DoctorDTO.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        DoctorDTO newDoctor = (DoctorDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstname","empty");
        if (newDoctor.getFirstname().length() < 2 || newDoctor.getFirstname().length() > 32) {
            errors.rejectValue("firstname", "size");
        }
        // Check if firstname contains numbers
        if (newDoctor.getFirstname().matches(".*\\d+.*")) {
            errors.rejectValue("firstname", "containsIntegers");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (newDoctor.getLastname().length() < 2 || newDoctor.getLastname().length() > 32) {
            errors.rejectValue("lastname", "size");
        }
        // Check if lastname contains numbers
        if (newDoctor.getLastname().matches(".*\\d+.*")) {
            errors.rejectValue("lastname", "containsIntegers");
        }
    }
}
