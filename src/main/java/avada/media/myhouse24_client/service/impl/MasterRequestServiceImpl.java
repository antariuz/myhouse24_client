package avada.media.myhouse24_client.service.impl;

import avada.media.myhouse24_client.model.MasterRequest;
import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.dto.MasterRequestDTO;
import avada.media.myhouse24_client.model.response.ResponseByPage;
import avada.media.myhouse24_client.model.dto.RoleDTO;
import avada.media.myhouse24_client.model.dto.StatusDTO;
import avada.media.myhouse24_client.repo.FlatRepo;
import avada.media.myhouse24_client.repo.MasterRequestRepo;
import avada.media.myhouse24_client.repo.RoleRepo;
import avada.media.myhouse24_client.repo.UserRepo;
import avada.media.myhouse24_client.service.MasterRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MasterRequestServiceImpl implements MasterRequestService {

    private final MasterRequestRepo masterRequestRepo;
    private final FlatRepo flatRepo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public ResponseByPage<MasterRequestDTO> getAllUserMasterRequestsByPage(String userEmail, Integer page, Integer limit) {
        User user = userRepo.getUserByEmail(userEmail);
        Page<MasterRequest> masterRequestsPage = masterRequestRepo.getAllByUser(user, PageRequest.of(page - 1, limit));
        ResponseByPage<MasterRequestDTO> masterRequestsDTOByPage = new ResponseByPage<>();
        masterRequestsDTOByPage.setItemsCount(masterRequestsPage.getTotalElements());
        masterRequestsDTOByPage.setPagesCount(masterRequestsPage.getTotalPages());
        for (MasterRequest masterRequest : masterRequestsPage) {
            MasterRequestDTO masterRequestDTO = new MasterRequestDTO();
            masterRequestDTO.setId(masterRequest.getId());
            if (masterRequest.getRole() != null)
                masterRequestDTO.setRole(new RoleDTO(masterRequest.getRole().getId(), masterRequest.getRole().getTitle()));
            else masterRequestDTO.setRole(new RoleDTO(0L, "Любой специалист"));
            masterRequestDTO.setDescription(masterRequest.getDescription());
            masterRequestDTO.setRequestedDate(masterRequest.getRequestedDate());
            masterRequestDTO.setStatus(new StatusDTO(masterRequest.getStatus().name(), masterRequest.getStatus().getTitle()));
            masterRequestsDTOByPage.getData().add(masterRequestDTO);
        }
        return masterRequestsDTOByPage;
    }

    @Override
    public void saveMasterRequest(MasterRequestDTO masterRequestDTO, String userEmail) {
        MasterRequest masterRequest = new MasterRequest();
        masterRequest.setUser(userRepo.getUserByEmail(userEmail));
        if (masterRequestDTO.getRole().getId() != 0) {
            masterRequest.setRole(roleRepo.findById(masterRequestDTO.getRole().getId()).orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + masterRequestDTO.getRole().getId())));
        }
        masterRequest.setFlat(flatRepo.findById(masterRequestDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + masterRequestDTO.getFlat().getId())));
        masterRequest.setRequestedDate(masterRequestDTO.getRequestedDate());
        masterRequest.setDescription(masterRequestDTO.getDescription());
        masterRequest.setStatus(MasterRequest.Status.NEW);
        masterRequestRepo.save(masterRequest);
        log.info("Master Request with id {} has been successfully created", masterRequest.getId());
    }

    @Override
    public void deleteMasterRequest(Long id) {
        masterRequestRepo.deleteById(id);
        log.info("Master Request with id {} has been successfully deleted", id);
    }

    @Override
    public List<RoleDTO> getRolesExceptDirectorAndManager() {
        return roleRepo.getRolesExceptDirectorAndManager()
                .stream()
                .map(role -> new RoleDTO(role.getId(), role.getTitle()))
                .collect(Collectors.toList());
    }

}
