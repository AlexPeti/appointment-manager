package gr.aueb.cf.appointmentmanager.controller;

import gr.aueb.cf.appointmentmanager.dto.UserDTO;
import gr.aueb.cf.appointmentmanager.model.User;
import gr.aueb.cf.appointmentmanager.service.IUserService;
import gr.aueb.cf.appointmentmanager.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final IUserService userService;
    private final UserValidator userValidator;

    public RegistrationController(IUserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
        userValidator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            User registeredUser = userService.registerUser(userDTO);
            if (registeredUser != null) {
                return "redirect:/login";
            } else {
                model.addAttribute("error", "Registration failed");
                return "register";
            }
        }
    }
}
