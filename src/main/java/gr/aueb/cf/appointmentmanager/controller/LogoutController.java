package gr.aueb.cf.appointmentmanager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller class for handling user logout.
 */
@Controller
public class LogoutController {

    /**
     * Logs out the user by invalidating the session, clearing the security context, and deleting any cookies.
     * Redirects to the login page after logout.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @return a redirect to the login page
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Clear authentication
        SecurityContextHolder.clearContext();
        // Clear cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        // Redirect to login page
        return "redirect:/login";
    }
}
