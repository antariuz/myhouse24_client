package avada.media.myhouse24_client.repo;

import avada.media.myhouse24_client.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {
}
