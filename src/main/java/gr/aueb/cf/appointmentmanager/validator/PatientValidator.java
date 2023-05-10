package gr.aueb.cf.appointmentmanager.validator;

import gr.aueb.cf.appointmentmanager.dto.PatientDTO;
import gr.aueb.cf.appointmentmanager.service.IPatientService;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.ValidationUtils;

import org.springframework.validation.Validator;

@Component
public class PatientValidator implements Validator {

    private final IPatientService patientService;

    @Autowired
    public PatientValidator(IPatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PatientDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PatientDTO newPatient = (PatientDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "empty");
        if (newPatient.getFirstname().length() < 2 || newPatient.getFirstname().length() > 32) {
            errors.rejectValue("firstname", "size");
        }
        // Check if firstname contains integers
        if (newPatient.getFirstname().matches(".*\\d+.*")) {
            errors.rejectValue("firstname", "containsIntegers");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "empty");
        if (newPatient.getLastname().length() < 2 || newPatient.getLastname().length() > 32) {
            errors.rejectValue("lastname", "size");
        }
        // Check if lastname contains integers
        if (newPatient.getLastname().matches(".*\\d+.*")) {
            errors.rejectValue("lastname", "containsIntegers");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "empty");
        if (newPatient.getPhoneNumber().length() != 10 || !newPatient.getPhoneNumber().matches("[0-9]")) {
            errors.rejectValue("phoneNumber","invalidPhoneNumber");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ssn", "empty");
//        if (newPatient.getSsn().length() != 10 || !newPatient.getSsn().matches("^[0-9]{11}$")) {
//            errors.rejectValue("ssn", "invalidSsn");
//        }
    }
}
