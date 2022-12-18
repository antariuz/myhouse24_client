package avada.media.myhouse24_client.model.dto;

import avada.media.myhouse24_client.model.Flat;
import avada.media.myhouse24_client.model.Floor;
import avada.media.myhouse24_client.model.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlatDTO {

    private Long id;
    private Long number;
    private Double totalSquare;
    private Floor floor;
    private Section section;
    private BuildingDTO building;
    private UserDTO user;
    private TariffDTO tariff;
    private AccountDTO account;
    private Double balance;
    private String title;

    public void setTitle(Long number, String buildingName) {
        this.title = "№" + number + ", " + buildingName;
    }

    public void setTitle(Long number) {
        this.title = "№" + number;
    }

    public FlatDTO(Long id, Long number) {
        this.id = id;
        this.title = "№" + number;
    }

    public FlatDTO(Long id, String buildingTitle, Long number) {
        this.id = id;
        this.title = buildingTitle + ", №" + number;
    }

    public FlatDTO flatToDto(Flat flat) {
        FlatDTO flatDTO = new FlatDTO();
        flatDTO.setId(flat.getId());
        flatDTO.setNumber(flat.getNumber());
        flatDTO.setTotalSquare(flat.getTotalSquare());
        flatDTO.setFloor(flat.getFloor());
        flatDTO.setSection(flat.getSection());
        if (flat.getBuilding() != null) {
            flatDTO.setBuilding(new BuildingDTO().buildingToDto(flat.getBuilding()));
        }
        if (flat.getAccount() != null) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setUniqueNumber(flat.getAccount().getUniqueNumber());
            flatDTO.setAccount(accountDTO);
            flatDTO.setBalance(flat.getAccount().getBalance());
        }
        return flatDTO;
    }

}
