package ua.univerpulse.webchat.mvc.controller;

import ua.univerpulse.webchat.mvc.dto.ChatUserDto;
import ua.univerpulse.webchat.mvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView registrationUser(@ModelAttribute("user") ChatUserDto userDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("registrationHandler", "registration");
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") @Validated ChatUserDto userDto, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(err -> err.toString()).collect(Collectors.toList());
            attributes.addFlashAttribute("error", errors);
            userDto.setPassword("");
            attributes.addFlashAttribute("user", userDto);
            return "redirect:/registration";
        }
        loginService.save(userDto);
        return "redirect:/";
    }
}
