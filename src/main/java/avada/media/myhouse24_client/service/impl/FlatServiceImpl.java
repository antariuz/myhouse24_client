package avada.media.myhouse24_client.service.impl;

import avada.media.myhouse24_client.model.Flat;
import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.dto.FlatDTO;
import avada.media.myhouse24_client.model.dto.ServiceDTO;
import avada.media.myhouse24_client.model.dto.TariffDTO;
import avada.media.myhouse24_client.model.dto.TariffServiceDTO;
import avada.media.myhouse24_client.repo.FlatRepo;
import avada.media.myhouse24_client.repo.UserRepo;
import avada.media.myhouse24_client.service.FlatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlatServiceImpl implements FlatService {

    private final FlatRepo flatRepo;
    private final UserRepo userRepo;

    @Override
    public List<FlatDTO> getUserFlats(String userEmail) {
        User user = userRepo.getUserByEmail(userEmail);
        return flatRepo.findAllByUserId(user.getId())
                .stream()
                .map(flat -> {
                    FlatDTO flatDTO = new FlatDTO();
                    flatDTO.setId(flat.getId());
                    flatDTO.setTitle(flat.getNumber(), flat.getBuilding().getTitle());
                    return flatDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TariffDTO getTariffDTOByFlat(Long flatId) {
        Flat flat = flatRepo.findById(flatId).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + flatId));
        TariffDTO tariffDTO = new TariffDTO();
        if (flat.getTariff() != null) {
            tariffDTO.setId(flat.getTariff().getId());
            Hibernate.initialize(flat.getTariff().getTariffServices());
            List<TariffServiceDTO> tariffServiceDTOList = flat.getTariff().getTariffServices().stream()
                    .map(tariffService -> {
                        TariffServiceDTO tariffServiceDTO = new TariffServiceDTO();
                        tariffServiceDTO.setId(tariffService.getId());
                        tariffServiceDTO.setPrice(tariffService.getPrice());
                        ServiceDTO serviceDTO = new ServiceDTO();
                        serviceDTO.setId(tariffService.getService().getId());
                        serviceDTO.setName(tariffService.getService().getName());
                        serviceDTO.setUnit(tariffService.getService().getUnit());
                        tariffServiceDTO.setService(serviceDTO);
                        return tariffServiceDTO;
                    })
                    .collect(Collectors.toList());
            tariffDTO.setTariffServices(tariffServiceDTOList);
        }
        return tariffDTO;
    }

    @Override
    @Transactional
    public FlatDTO getUserFlat(String userEmail, Long flatId) {
        User user = userRepo.getUserByEmail(userEmail);
        Flat userFlat = flatRepo.findByUserIdAndId(user.getId(), flatId);
        return new FlatDTO().flatToDto(userFlat);
    }

}
