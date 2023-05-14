package gr.aueb.cf.appointmentmanager.service.exceptions;

public class InvalidDoctorException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidDoctorException(String message) {
        super(message);
    }
}
