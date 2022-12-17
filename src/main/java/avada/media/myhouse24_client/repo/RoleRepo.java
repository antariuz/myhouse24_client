package avada.media.myhouse24_client.repo;

import avada.media.myhouse24_client.model.systemSettings.pages.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM role WHERE NOT name = 'ROLE_DIRECTOR' AND NOT name = 'ROLE_ACCOUNTANT' ORDER BY id", nativeQuery = true)
    List<Role> getRolesExceptDirectorAndManager();

}
