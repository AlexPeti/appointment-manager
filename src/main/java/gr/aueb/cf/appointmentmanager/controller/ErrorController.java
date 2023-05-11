package gr.aueb.cf.appointmentmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for handling errors in the application.
 */
@ControllerAdvice
public class ErrorController {

    /**
     * Handles requests to the /error endpoint.
     *
     * @return the name of the HTML template to use for rendering the error page
     */
    @GetMapping("/error")
    public String handleError() {
        return "error";
    }

    /**
     * Handles exceptions of type IllegalArgumentException and IllegalStateException.
     *
     * @param ex the exception to handle
     * @return a ResponseEntity with a message and status code of BAD_REQUEST
     */
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other types of exceptions.
     *
     * @param e the exception to handle
     * @return a ResponseEntity with a message and status code of INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}
