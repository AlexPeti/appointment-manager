package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.UserDTO;
import gr.aueb.cf.appointmentmanager.model.User;
import gr.aueb.cf.appointmentmanager.repository.UserRepository;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User registerUser(UserDTO userToRegister) {
        User user = modelMapper.map(userToRegister, User.class);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDTO userDTO) throws EntityNotFoundException {
        User user = userRepository.findUserById(userDTO.getId());

        if (user == null) {
            throw new EntityNotFoundException(User.class, userDTO.getId());
        }
        modelMapper.map(userDTO, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new EntityNotFoundException(User.class,id);
        }

        userRepository.delete(user);
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException {
        User user = userRepository.findByUsernameEquals(username);

        if (user == null) {
            throw new EntityNotFoundException(User.class, null);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new EntityNotFoundException(User.class, id);
        }
        return user;
    }

    @Override
    public boolean usernameAlreadyExists(String username) {
        return userRepository.usernameExists(username);
    }
}
