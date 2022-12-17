package avada.media.myhouse24_client.repo;

import avada.media.myhouse24_client.model.MasterRequest;
import avada.media.myhouse24_client.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRequestRepo extends JpaRepository<MasterRequest, Long>, JpaSpecificationExecutor<MasterRequest> {

    Page<MasterRequest> getAllByUser(User user, Pageable pageable);

}
