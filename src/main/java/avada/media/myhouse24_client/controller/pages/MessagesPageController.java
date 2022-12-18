package avada.media.myhouse24_client.controller.pages;

import avada.media.myhouse24_client.model.dto.MessageDTO;
import avada.media.myhouse24_client.model.response.ResponseByPage;
import avada.media.myhouse24_client.model.request.MessageRequest;
import avada.media.myhouse24_client.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessagesPageController {

    private final MessageService messageService;

    @GetMapping({"/", ""})
    public ModelAndView showMessagesPage() {
        return new ModelAndView("pages/messages");
    }

    @GetMapping("get-all-user-messages-by-page")
    public @ResponseBody ResponseByPage<MessageDTO> getAllUserMessagesByPage(MessageRequest messageRequest) {
        return messageService.getAllUserMessages(SecurityContextHolder.getContext().getAuthentication().getName(), messageRequest);
    }

    @DeleteMapping("{ids}/delete")
    public ResponseEntity<Void> deleteMessagesById(@PathVariable List<Long> ids) {
        messageService.deleteMessagesById(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-user-message/{id}")
    public @ResponseBody MessageDTO getUserMessage(@PathVariable Long id) {
        return messageService.getUserMessage(SecurityContextHolder.getContext().getAuthentication().getName(), id);
    }

}
