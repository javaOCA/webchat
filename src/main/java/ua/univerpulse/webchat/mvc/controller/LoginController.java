package ua.univerpulse.webchat.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.domain.RoleEnum;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;
import ua.univerpulse.webchat.mvc.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {

    private final LoginService loginService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET, name = "loginUser")
    public ModelAndView loginUser(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new ChatUserDto());
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("loginHandler", "./login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String verifyLogin(@ModelAttribute("user") ChatUserDto userDto,
                              HttpSession session,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              RedirectAttributes attributes) {
        ChatUser chatUser = loginService.verifyLogin(userDto.getLogin(), userDto.getPassword());
        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.USER) {
            session.setAttribute("user", chatUser);
            dropHttpOnlyFlag(session.getId(), request, response);
            return "redirect:/chat";
        }
        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.ADMIN) {
            session.setAttribute("user", chatUser);
            return "redirect:/admin";
        }
        attributes.addFlashAttribute("error", messageSource.getMessage("login.incorrect", null, LocaleContextHolder.getLocale()));
        return "redirect:/login";
    }

    public void dropHttpOnlyFlag(String sessionId, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("JSESSIONID")){
                cookie.setHttpOnly(false);
                response.addCookie(cookie);
            }
        }
    }
}
