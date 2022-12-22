package avada.media.myhouse24_client.service;

import avada.media.myhouse24_client.model.dto.MessageDTO;
import avada.media.myhouse24_client.model.request.MessageRequest;
import avada.media.myhouse24_client.model.response.ResponseByPage;

import java.util.List;

public interface MessageService {

    ResponseByPage<MessageDTO> getAllUserMessages(String userEmail, MessageRequest messageRequest);

    void deleteMessagesById(String userEmail, List<Long> ids);

    MessageDTO getUserMessage(String userEmail, Long id);

}
