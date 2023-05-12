package gr.aueb.cf.appointmentmanager.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class implements the PasswordEncoder interface to provide password
 * encoding functionality using the BCryptPasswordEncoder from Spring Security.
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Encodes the specified raw password using BCryptPasswordEncoder.
     *
     * @param rawPassword the raw password to be encoded
     * @return the encoded password
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Matches the specified raw password with the specified encoded password.
     *
     * @param rawPassword the raw password to be matched
     * @param encodedPassword the encoded password to be matched against
     * @return true if the raw password matches the encoded password, false otherwise
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

