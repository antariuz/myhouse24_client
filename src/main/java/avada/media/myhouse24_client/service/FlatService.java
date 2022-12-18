package avada.media.myhouse24_client.service;

import avada.media.myhouse24_client.model.dto.FlatDTO;
import avada.media.myhouse24_client.model.dto.TariffDTO;

import java.util.List;

public interface FlatService {

    List<FlatDTO> getUserFlats(String userEmail);
    TariffDTO getTariffDTOByFlat(Long flatId);
    FlatDTO getUserFlat(String userEmail, Long flatId);

}
