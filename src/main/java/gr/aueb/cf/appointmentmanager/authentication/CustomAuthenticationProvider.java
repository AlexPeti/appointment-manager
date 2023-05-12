package gr.aueb.cf.appointmentmanager.authentication;

import gr.aueb.cf.appointmentmanager.model.User;
import gr.aueb.cf.appointmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Locale;

/**
 * An authentication provider implementation that handles custom authentication logic using Spring Security.
 * It is responsible for checking user credentials against those stored in a UserRepository instance, and
 * authenticating the user if the credentials are valid. Passwords are encoded using the BCryptPasswordEncoder
 * provided by Spring Security.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Autowired
    public CustomAuthenticationProvider(UserRepository userRepository, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
    }

    private MessageSourceAccessor accessor;

    /**
     * Initializes the accessor field with a new MessageSourceAccessor instance.
     */
    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
    }

    /**
     * Attempts to authenticate the given authentication object, checking the user credentials against those stored in the UserRepository.
     * If the credentials are valid, a new UsernamePasswordAuthenticationToken instance is returned containing the user's username and an
     * empty list of granted authorities.
     * If the credentials are invalid, a BadCredentialsException is thrown.
     *
     * @param authentication the Authentication object to authenticate
     * @return a new UsernamePasswordAuthenticationToken instance containing the user's username and an empty list of granted authorities,
     * if authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUsernameEquals(username);

        if (user == null) {
            throw new BadCredentialsException(accessor.getMessage("badCredentials"));
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(accessor.getMessage("badCredentials"));
        }
        return new UsernamePasswordAuthenticationToken(username, password, Collections.<GrantedAuthority>emptyList());
    }

    /**
     * Indicates whether the AuthenticationProvider implementation supports the given authentication class.
     * In this case, the class is UsernamePasswordAuthenticationToken.
     *
     * @param authentication the authentication class to check.
     * @return true if the implementation supports the given authentication class, false otherwise.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
