package ua.univerpulse.webchat.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.univerpulse.webchat.mvc.domain.ChatUser;
import ua.univerpulse.webchat.mvc.domain.RoleEnum;
import ua.univerpulse.webchat.mvc.dto.BanDto;
import ua.univerpulse.webchat.mvc.dto.BanUserDto;
import ua.univerpulse.webchat.mvc.dto.ChatUserDto;
import ua.univerpulse.webchat.mvc.dto.ResponseDto;
import ua.univerpulse.webchat.mvc.service.BanService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class AdminController {

    @Resource
    private Environment env;
    private BanService banService;

    @Autowired
    public AdminController(BanService banService) {
        this.banService = banService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView getAdminPage(HttpSession session) {
        ChatUser chatUser = (ChatUser) session.getAttribute("user");
        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.ADMIN) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("usersUrl", env.getProperty("usersUrl"));
            modelAndView.setViewName("admin");
            return modelAndView;
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ResponseEntity<BanDto> addUserToBan(@RequestBody ChatUserDto chatUserDto, HttpSession session) {
        ChatUser chatUser = (ChatUser) session.getAttribute("user");
        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.ADMIN) {
            banService.addUserToBanList(chatUserDto);
            BanDto banDto = new BanDto();
            banDto.setAuth("yes");
            banDto.setSuccess("yes");
            return new ResponseEntity<BanDto>(banDto, HttpStatus.OK);
        }
        BanDto banDto = new BanDto();
        banDto.setAuth("no");
        banDto.setSuccess("no");
        return new ResponseEntity<BanDto>(banDto, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BanDto> deleteUserFromBanList(@PathVariable("id") Long id,
                                                        HttpSession session) {
        ChatUser chatUser = (ChatUser) session.getAttribute("user");
        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.ADMIN) {
            banService.deleteUserFromBanList(id);
            BanDto banDto = new BanDto();
            banDto.setAuth("yes");
            banDto.setSuccess("yes");
            return new ResponseEntity<BanDto>(banDto, HttpStatus.OK);
        }
        BanDto banDto = new BanDto();
        banDto.setAuth("no");
        banDto.setSuccess("no");
        return new ResponseEntity<BanDto>(banDto, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/admin/getUsers", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> getAllUsersExceptAdmin(HttpSession session) {
        ChatUser chatUser = (ChatUser) session.getAttribute("user");
        if (Objects.nonNull(chatUser) && chatUser.getRole().getRole() == RoleEnum.ADMIN) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setAuth("yes");
            List<BanUserDto> banUserDtos = new ArrayList<>();
            List<ChatUserDto> chatUserDtos = banService.getAllUsersExceptAdmin();
            for (ChatUserDto chatUserDto: chatUserDtos) {
                BanUserDto banUserDto = new BanUserDto();
                banUserDto.setLogin(chatUserDto.getLogin());
                if (banService.isUserBaned(banUserDto)) {
                    ResponseEntity<BanDto> responseEntity = methodOn(AdminController.class)
                            .deleteUserFromBanList(chatUserDto.getId(), session);
                    Link link = linkTo(responseEntity).withRel("remove");
                    banUserDto.add(link);
                    banUserDtos.add(banUserDto);
                } else {
                    ResponseEntity<BanDto> responseEntity = methodOn(AdminController.class)
                            .addUserToBan(chatUserDto, session);
                    Link link = linkTo(responseEntity).withRel("add");
                    banUserDto.add(link);
                    banUserDtos.add(banUserDto);
                }
            }
            responseDto.setUsers(banUserDtos);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setAuth("no");
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.UNAUTHORIZED);
        }
    }
}