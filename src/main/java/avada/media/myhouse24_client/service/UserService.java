package avada.media.myhouse24_client.service;

import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.dto.FlatDTO;
import avada.media.myhouse24_client.model.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    boolean checkEmail(Long id, String email);

    void updateUser(Long id, User user, MultipartFile profileImage);

    User getUserByEmail(String email);

    UserDTO getUserInfo(String email);

    UserDTO getUser(String email);

    List<FlatDTO> getAllUserFlats(String userEmail);

}
