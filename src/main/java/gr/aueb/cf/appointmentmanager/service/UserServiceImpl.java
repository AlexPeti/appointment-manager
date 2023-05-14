package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.UserDTO;
import gr.aueb.cf.appointmentmanager.model.User;
import gr.aueb.cf.appointmentmanager.repository.UserRepository;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User registerUser(UserDTO userToRegister) {
        User user = modelMapper.map(userToRegister, User.class);
        user.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(UserDTO userDTO) throws EntityNotFoundException {
        User user = userRepository.findUserById(userDTO.getId());

        if (user == null) {
            throw new EntityNotFoundException(User.class, userDTO.getId());
        }
        modelMapper.map(userDTO, user);
        return userRepository.save(user);
    }

    @Transactional
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
