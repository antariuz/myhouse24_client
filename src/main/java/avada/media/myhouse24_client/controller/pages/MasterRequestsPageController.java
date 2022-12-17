package avada.media.myhouse24_client.controller.pages;

import avada.media.myhouse24_client.model.dto.*;
import avada.media.myhouse24_client.model.response.ResponseByPage;
import avada.media.myhouse24_client.service.MasterRequestService;
import avada.media.myhouse24_client.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/master-requests")
@RequiredArgsConstructor
public class MasterRequestsPageController {

    private final MasterRequestService masterRequestService;
    private final UserService userService;

    @GetMapping({"/", ""})
    public ModelAndView showMasterRequestsPage() {
        return new ModelAndView("pages/master-requests");
    }

    @GetMapping("get-all-user-master-requests-by-page")
    public @ResponseBody ResponseByPage<MasterRequestDTO> getAllUserMasterRequestsByPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                         @RequestParam(required = false, defaultValue = "1") Integer limit) {
        return masterRequestService.getAllUserMasterRequestsByPage(SecurityContextHolder.getContext().getAuthentication().getName(), page, limit);
    }

    @GetMapping("get-roles-except-director-and-manager")
    public @ResponseBody List<RoleDTO> getRolesExceptDirectorAndManager() {
        return masterRequestService.getRolesExceptDirectorAndManager();
    }

    @GetMapping("get-all-user-flats")
    public @ResponseBody List<FlatDTO> getAllUserFlats() {
        return userService.getAllUserFlats(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveMasterRequest(@RequestBody MasterRequestDTO masterRequestDTO) {
        masterRequestService.saveMasterRequest(masterRequestDTO, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteMasterRequest(@PathVariable Long id) {
        masterRequestService.deleteMasterRequest(id);
        return ResponseEntity.ok().build();
    }

}
