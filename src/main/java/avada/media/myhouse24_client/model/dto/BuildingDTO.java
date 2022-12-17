package avada.media.myhouse24_client.model.dto;

import avada.media.myhouse24_client.model.Building;
import avada.media.myhouse24_client.model.Floor;
import avada.media.myhouse24_client.model.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuildingDTO {

    private Long id;
    private String title;
    private String address;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private List<Section> sections;
    private List<Floor> floors;
    private List<StaffDTO> staffList;

    public BuildingDTO buildingToDto(Building building) {
        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setId(building.getId());
        buildingDTO.setTitle(building.getTitle());
        buildingDTO.setAddress(building.getAddress());
        buildingDTO.setImage1(building.getImage1());
        buildingDTO.setImage2(building.getImage2());
        buildingDTO.setImage3(building.getImage3());
        buildingDTO.setImage4(building.getImage4());
        buildingDTO.setImage5(building.getImage5());
        Hibernate.initialize(building.getSections());
        Hibernate.initialize(building.getFloors());
        Hibernate.initialize(building.getStaff());
        buildingDTO.setSections(building.getSections());
        buildingDTO.setFloors(building.getFloors());
        List<StaffDTO> staffList = building.getStaff().stream()
                .map(staff -> {
                    StaffDTO staffDTO = new StaffDTO();
                    staffDTO.setFullName(staff.getLastname(), staff.getFirstname());
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setTitle(staff.getRole().getTitle());
                    staffDTO.setRole(roleDTO);
                    return staffDTO;
                })
                .collect(Collectors.toList());
        buildingDTO.setStaffList(staffList);
        return buildingDTO;
    }

}