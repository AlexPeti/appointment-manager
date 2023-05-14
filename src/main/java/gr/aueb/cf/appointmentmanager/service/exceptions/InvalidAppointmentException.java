package gr.aueb.cf.appointmentmanager.service.exceptions;

/**
 * Exception thrown when an appointment object is created with
 * a doctor who is already booked for that date and time.
 */
public class InvalidAppointmentException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidAppointmentException(String message) {
        super(message);
    }
}
