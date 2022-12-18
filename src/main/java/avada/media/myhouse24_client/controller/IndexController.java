package avada.media.myhouse24_client.controller;

import avada.media.myhouse24_client.model.dto.FlatDTO;
import avada.media.myhouse24_client.model.dto.MessageNotificationDTO;
import avada.media.myhouse24_client.model.dto.UserDTO;
import avada.media.myhouse24_client.service.FlatService;
import avada.media.myhouse24_client.service.MessageNotificationService;
import avada.media.myhouse24_client.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;
    private final FlatService flatService;
    private final MessageNotificationService messageNotificationService;

    @RequestMapping({"/", ""})
    public ModelAndView showDefaultPage() {
        return new ModelAndView("redirect:/profile");
    }

    @GetMapping("get-user-info")
    public @ResponseBody UserDTO getUserInfo() {
        return userService.getUserInfo(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("get-user-flats")
    public @ResponseBody List<FlatDTO> getUserFlats() {
        return flatService.getUserFlats(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("get-user-unread-messages")
    public @ResponseBody List<MessageNotificationDTO> getUserUnreadMessages() {
        return messageNotificationService.getUserUnreadMessages(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
