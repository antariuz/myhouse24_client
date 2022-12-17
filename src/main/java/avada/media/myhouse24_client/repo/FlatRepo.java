package avada.media.myhouse24_client.repo;

import avada.media.myhouse24_client.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatRepo extends JpaRepository<Flat, Long>, JpaSpecificationExecutor<Flat> {

    List<Flat> findAllByUserId(Long id);

    Flat findByUserIdAndId(Long userId, Long flatId);

}
