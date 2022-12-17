package avada.media.myhouse24_client.service;

import avada.media.myhouse24_client.model.dto.MasterRequestDTO;
import avada.media.myhouse24_client.model.response.ResponseByPage;
import avada.media.myhouse24_client.model.dto.RoleDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MasterRequestService {

    ResponseByPage<MasterRequestDTO> getAllUserMasterRequestsByPage(String userEmail, Integer page, Integer limit);

    void saveMasterRequest(@RequestBody MasterRequestDTO masterRequestDTO, String userEmail);

    void deleteMasterRequest(@PathVariable Long id);

    List<RoleDTO> getRolesExceptDirectorAndManager();

}
