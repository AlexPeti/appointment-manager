package gr.aueb.cf.appointmentmanager.service.exceptions;

/**
 * Exception thrown when a patient has invalid fields when created
 * by the user.
 */
public class InvalidPatientException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidPatientException(String message) {
        super(message);
    }
}
