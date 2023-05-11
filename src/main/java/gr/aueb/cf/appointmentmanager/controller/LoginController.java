package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.authentication.CustomAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Controller for handling login-related requests.
 */
@Controller
public class LoginController {

    /**
     * Handles requests to "/login" path.
     * @param model the model to be used for rendering the view
     * @param principal the currently logged in user
     * @param request the HTTP servlet request
     * @return the name of the view to be rendered
     * @throws Exception if an error occurs during processing
     */
    @GetMapping(path = "/login")
    String login(Model model, Principal principal, HttpServletRequest request) throws Exception {

        String referer = request.getHeader("Referer");
        request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME,
                referer);

        if (principal == null) {
            return "login";
        } else {
            return "redirect:/dashboard";
        }

    }

    /**
     * Handles requests to "/" path.
     * @param model the model to be used for rendering the view
     * @param principal the currently logged in user
     * @param request the HTTP servlet request
     * @return the name of the view to be rendered
     * @throws Exception if an error occurs during processing
     */
    @GetMapping(path = "/")
    String root(Model model, Principal principal, HttpServletRequest request) throws Exception {
        if (principal == null) {
            return "login";
        } else {
            return "redirect:/dashboard";
        }
    }
}
