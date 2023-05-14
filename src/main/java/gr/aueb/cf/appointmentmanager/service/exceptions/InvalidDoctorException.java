package gr.aueb.cf.appointmentmanager.service.exceptions;

/**
 * Exception thrown when a user creates a
 * doctor with invalid first or last name.
 */
public class InvalidDoctorException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidDoctorException(String message) {
        super(message);
    }
}
