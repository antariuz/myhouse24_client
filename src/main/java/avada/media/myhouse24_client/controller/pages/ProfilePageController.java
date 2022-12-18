package avada.media.myhouse24_client.controller.pages;

import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.dto.UserDTO;
import avada.media.myhouse24_client.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfilePageController {

    private final UserService userService;

    @GetMapping({"/", ""})
    public ModelAndView showUsersPage() {
        return new ModelAndView("pages/profile");
    }

    @GetMapping("get-user")
    public @ResponseBody UserDTO getUserInfo() {
        return userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("email-check")
    public @ResponseBody boolean checkEmail(Long id, String email) {
        return userService.checkEmail(id, email);
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> saveUser(@PathVariable Long id,
                                         @RequestPart User user,
                                         @RequestPart(required = false) MultipartFile profileImage) {
        userService.updateUser(id, user, profileImage);
        return ResponseEntity.ok().build();
    }

}
