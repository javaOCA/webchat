package ua.univerpulse.webchat.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET, name = "loginUser")
    public ModelAndView loginUser() {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }
}
