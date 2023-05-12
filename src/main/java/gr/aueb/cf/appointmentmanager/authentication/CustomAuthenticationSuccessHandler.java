package gr.aueb.cf.appointmentmanager.authentication;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CustomAuthenticationSuccessHandler is a Spring Security handler that is used to redirect users to the page they
 * initially wanted to access before they were redirected to the login page due to not being authenticated.
 * If there is no specific redirect URL in the session attributes, it sets the default target URL to "/api/dashboard".
 */
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REDIRECT_URL_SESSION_ATTRIBUTE_NAME = "REDIRECT_URL";

    /**
     * Sets the target URL for the redirect.
     * If a redirect URL is found in the session attributes, it is used.
     * Otherwise, it sets the default target URL to "/api/dashboard".
     * It then removes the redirect URL from the session attributes and proceeds with the redirect.
     *
     * @param request The request object
     * @param response The response object
     * @param authentication The authentication object
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Object redirectURLObject = request.getSession().getAttribute(REDIRECT_URL_SESSION_ATTRIBUTE_NAME);

        if (redirectURLObject != null) {
            setDefaultTargetUrl(redirectURLObject.toString());
        } else {
            setDefaultTargetUrl("/api/dashboard");
        }
        request.getSession().removeAttribute(REDIRECT_URL_SESSION_ATTRIBUTE_NAME);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
