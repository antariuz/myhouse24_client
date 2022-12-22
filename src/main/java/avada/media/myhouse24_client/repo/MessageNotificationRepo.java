package avada.media.myhouse24_client.repo;

import avada.media.myhouse24_client.model.MessageNotification;
import avada.media.myhouse24_client.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageNotificationRepo extends JpaRepository<MessageNotification, Long> {

    List<MessageNotification> getAllByWasViewedAndUser(boolean wasViewed, User user);

    Optional<MessageNotification> getMessageNotificationByMessageIdAndUserId(Long messageId, Long userId);

}
