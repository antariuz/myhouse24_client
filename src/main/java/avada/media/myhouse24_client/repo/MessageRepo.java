package avada.media.myhouse24_client.repo;

import avada.media.myhouse24_client.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

    Message getMessageByIdAndUsersId(Long id, Long userId);

}
