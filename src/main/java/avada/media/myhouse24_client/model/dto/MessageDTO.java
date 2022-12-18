package avada.media.myhouse24_client.model.dto;

import avada.media.myhouse24_client.model.Floor;
import avada.media.myhouse24_client.model.Message;
import avada.media.myhouse24_client.model.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDTO {

    private Long id;
    private String subject;
    private String text;
    private StaffDTO staff;
    private boolean haveDebt;
    private BuildingDTO building;
    private Section section;
    private Floor floor;
    private FlatDTO flat;
    private UserDTO user;
    private Date createdAt;
    private String toWhom;

    public MessageDTO messageToDto(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setSubject(message.getSubject());
        messageDTO.setText(message.getText());
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setId(message.getStaff().getId());
        staffDTO.setFullName(message.getStaff().getLastname(), message.getStaff().getFirstname());
        messageDTO.setStaff(staffDTO);
        messageDTO.setCreatedAt(message.getCreatedAt());
        return messageDTO;
    }

}
