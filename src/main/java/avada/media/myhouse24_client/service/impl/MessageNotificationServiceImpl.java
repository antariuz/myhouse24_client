package avada.media.myhouse24_client.service.impl;

import avada.media.myhouse24_client.model.MessageNotification;
import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.dto.MessageNotificationDTO;
import avada.media.myhouse24_client.repo.MessageNotificationRepo;
import avada.media.myhouse24_client.service.MessageNotificationService;
import avada.media.myhouse24_client.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageNotificationServiceImpl implements MessageNotificationService {

    private final UserService userService;
    private final MessageNotificationRepo messageNotificationRepo;

    @Override
    @Transactional
    public List<MessageNotificationDTO> getUserUnreadMessages(String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        List<MessageNotification> messageNotifications = messageNotificationRepo.getAllByWasViewedAndUser(false, user);
        return messageNotifications
                .stream()
                .map(messageNotification -> new MessageNotificationDTO().messageNotificationToDto(messageNotification))
                .collect(Collectors.toList());
    }

}
