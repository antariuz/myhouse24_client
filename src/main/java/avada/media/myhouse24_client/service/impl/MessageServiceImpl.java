package avada.media.myhouse24_client.service.impl;

import avada.media.myhouse24_client.model.Message;
import avada.media.myhouse24_client.model.MessageNotification;
import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.dto.MessageDTO;
import avada.media.myhouse24_client.model.dto.StaffDTO;
import avada.media.myhouse24_client.model.request.MessageRequest;
import avada.media.myhouse24_client.model.response.ResponseByPage;
import avada.media.myhouse24_client.repo.MessageNotificationRepo;
import avada.media.myhouse24_client.repo.MessageRepo;
import avada.media.myhouse24_client.repo.UserRepo;
import avada.media.myhouse24_client.service.MessageService;
import avada.media.myhouse24_client.spec.MessageSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepo messageRepo;
    private final MessageSpec messageSpec;
    private final UserRepo userRepo;
    private final MessageNotificationRepo messageNotificationRepo;

    @Override
    public ResponseByPage<MessageDTO> getAllUserMessages(String userEmail, MessageRequest messageRequest) {
        User user = userRepo.getUserByEmail(userEmail);
        messageRequest.setUserId(user.getId());
        Page<Message> messages =
                messageRepo.findAll(messageSpec.getMessages(messageRequest), PageRequest.of(messageRequest.getPageIndex() - 1, messageRequest.getPageSize(), Sort.by(Sort.Direction.ASC, "id")));
        ResponseByPage<MessageDTO> response = new ResponseByPage<>();
        response.setItemsCount(messages.getTotalElements());
        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(message.getId());
            messageDTO.setSubject(message.getSubject());
            messageDTO.setText(message.getText());
            messageDTO.setCreatedAt(message.getCreatedAt());
            StaffDTO staffDTO = new StaffDTO();
            staffDTO.setEmail(message.getStaff().getEmail());
            staffDTO.setFullName(message.getStaff().getLastname(), message.getStaff().getFirstname());
            messageDTO.setStaff(staffDTO);
            response.getData().add(messageDTO);
        }
        return response;
    }

    @Override
    @Transactional
    public void deleteMessagesById(String userEmail, List<Long> ids) {
        User user = userRepo.getUserByEmail(userEmail);
        List<Message> messagesToDelete = messageRepo.findAllById(ids)
                .stream()
                .filter(message -> message.getUsers().remove(user))
                .collect(Collectors.toList());
        messageRepo.saveAll(messagesToDelete);
        log.info("Messages were successfully deleted with IDs {} of user {}", ids, userEmail);
    }

    @Override
    public MessageDTO getUserMessage(String userEmail, Long messageId) {
        User user = userRepo.getUserByEmail(userEmail);
        Message message = messageRepo.getMessageByIdAndUsersId(messageId, user.getId());
        if (message != null) {
            MessageNotification messageNotification =
                    messageNotificationRepo.getMessageNotificationByMessageIdAndUserId(message.getId(), user.getId())
                    .orElseThrow(() -> new EntityNotFoundException("MessageNotification not found"));
            messageNotification.setWasViewed(true);
            messageNotificationRepo.save(messageNotification);
            return new MessageDTO().messageToDto(message);
        } else return null;
    }

}
