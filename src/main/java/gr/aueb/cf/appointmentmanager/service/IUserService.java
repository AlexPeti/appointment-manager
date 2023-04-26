package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.UserDTO;
import gr.aueb.cf.appointmentmanager.model.User;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;

public interface IUserService {

    User registerUser(UserDTO userToRegister);
    User updateUser(UserDTO userDTO) throws EntityNotFoundException;
    void deleteUser(Long id) throws EntityNotFoundException;
    User getUserByUsername(String username) throws EntityNotFoundException;
    User getUserById(Long id) throws EntityNotFoundException;
    boolean usernameAlreadyExists(String username);
}
