package ua.univerpulse.webchat.mvc.controller;

import org.springframework.ui.Model;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.univerpulse.webchat.mvc.service.LoginService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {

    private LoginService loginService;
    @Autowired
    public RegistrationController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET, name = "registrationUser")
    public String registrationUser(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new ChatUserDto());
        }
        return "registration";
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") @Valid ChatUserDto userDto, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(err -> err.toString()).collect(Collectors.toList());
            attributes.addFlashAttribute("error", errors);
            attributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            userDto.setPassword("");
            attributes.addFlashAttribute("user", userDto);
            return "redirect:/registration";
        }
        loginService.save(userDto);
        return "redirect:/";
    }

}
