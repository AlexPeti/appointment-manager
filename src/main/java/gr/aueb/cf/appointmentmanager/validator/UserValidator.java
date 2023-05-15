package gr.aueb.cf.appointmentmanager.validator;

import gr.aueb.cf.appointmentmanager.dto.UserDTO;
import gr.aueb.cf.appointmentmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

/**
 * Validator used to validate userDTO objects before registration.
 * Checks if the username and password meet length requirements and
 * if the username is not already taken.
 */
@Component
public class UserValidator implements Validator {

    private final IUserService userService;

    @Autowired
    public UserValidator(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Checks if the class is supported by this validator.
     *
     * @param aClass the class to be checked
     * @return true if the class is supported, false otherwise
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.equals(aClass);
    }

    /**
     * Validates the target object.
     *
     * @param target the object to be validated
     * @param errors errors object to be used to report validation errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userToRegister = (UserDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty");
        if (userToRegister.getUsername().length() < 3 || userToRegister.getUsername().length() > 32) {
            errors.rejectValue("username", "size");
        }
        if (userService.usernameAlreadyExists(userToRegister.getUsername())) {
            errors.rejectValue("username", "duplicate");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty");
        if (userToRegister.getPassword().length() < 3 || userToRegister.getPassword().length() > 32) {
            errors.rejectValue("password", "size");
        }

        if (!Objects.equals(userToRegister.getPassword(), userToRegister.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "confirmation");
        }
    }
}
