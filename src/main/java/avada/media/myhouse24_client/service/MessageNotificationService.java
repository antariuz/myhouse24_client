package avada.media.myhouse24_client.service;

import avada.media.myhouse24_client.model.dto.MessageNotificationDTO;

import java.util.List;

public interface MessageNotificationService {

    List<MessageNotificationDTO> getUserUnreadMessages(String userEmail);

}
