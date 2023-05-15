package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.UserDTO;
import gr.aueb.cf.appointmentmanager.model.User;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;

/**
 * Service interface for User-related operations.
 */
public interface IUserService {
    /**
     * Registers a new user.
     *
     * @param userToRegister the UserDTO object containing user registration details
     * @return the created User object
     */
    User registerUser(UserDTO userToRegister);

    /**
     * Updates an existing user.
     *
     * @param userDTO the UserDTO object containing updated user details
     * @return the updated User object
     * @throws EntityNotFoundException if the user with the specified ID is not found
     */
    User updateUser(UserDTO userDTO) throws EntityNotFoundException;

    /**
     * Deletes a user.
     *
     * @param id the ID of the user to delete
     * @throws EntityNotFoundException if the user with the specified ID is not found
     */
    void deleteUser(Long id) throws EntityNotFoundException;

    /**
     * Retrieves a user by username.
     *
     * @param username the username of the user to retrieve
     * @return the User object with the specified username
     * @throws EntityNotFoundException if the user with the specified username is not found
     */
    User getUserByUsername(String username) throws EntityNotFoundException;

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the User object with the specified ID
     * @throws EntityNotFoundException if the user with the specified ID is not found
     */
    User getUserById(Long id) throws EntityNotFoundException;

    /**
     * Checks if a username already exists.
     *
     * @param username the username to check
     * @return true if the username already exists, false otherwise
     */
    boolean usernameAlreadyExists(String username);
}
