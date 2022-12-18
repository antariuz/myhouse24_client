package avada.media.myhouse24_client.service.impl;

import avada.media.myhouse24_client.model.Flat;
import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.model.common.FileUtil;
import avada.media.myhouse24_client.model.dto.AccountDTO;
import avada.media.myhouse24_client.model.dto.BuildingDTO;
import avada.media.myhouse24_client.model.dto.FlatDTO;
import avada.media.myhouse24_client.model.dto.UserDTO;
import avada.media.myhouse24_client.repo.FlatRepo;
import avada.media.myhouse24_client.repo.ProfileRepo;
import avada.media.myhouse24_client.repo.UserRepo;
import avada.media.myhouse24_client.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final ProfileRepo profileRepo;
    private final PasswordEncoder passwordEncoder;
    private final FlatRepo flatRepo;


    @Override
    public boolean checkEmail(Long id, String email) {
        if (email.equals(userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id))
                .getEmail())) {
            return true;
        } else return !userRepo.existsByEmail(email);
    }

    @Override
    @Transactional
    public void updateUser(Long id, User userRequested, MultipartFile profileImage) {
        User user = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        user.setEmail(userRequested.getEmail());
        if (userRequested.getPassword() != null) user.setPassword(passwordEncoder.encode(userRequested.getPassword()));
        Hibernate.initialize(user.getProfile());
        user.getProfile().setFirstname(userRequested.getProfile().getFirstname());
        user.getProfile().setMiddleName(userRequested.getProfile().getMiddleName());
        user.getProfile().setLastname(userRequested.getProfile().getLastname());
        user.getProfile().setPhoneNumber(userRequested.getProfile().getPhoneNumber());
        user.getProfile().setBirthdate(userRequested.getProfile().getBirthdate());
        user.getProfile().setViberLogin(userRequested.getProfile().getViberLogin());
        user.getProfile().setTelegramLogin(userRequested.getProfile().getTelegramLogin());
        user.getProfile().setNotes(userRequested.getProfile().getNotes());
        if (profileImage != null) {
            try {
                String changedFileName = "profile-image-" + id + Objects.requireNonNull(profileImage.getOriginalFilename()).substring(profileImage.getOriginalFilename().lastIndexOf("."));
                FileUtil.saveFile("users", changedFileName, profileImage);
                user.getProfile().setProfileImage(changedFileName);
            } catch (IOException e) {
                log.error("Not able to save file. File path: " + e.getMessage());
            }
        }
        profileRepo.save(user.getProfile());
        userRepo.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    @Override
    @Transactional
    public UserDTO getUserInfo(String email) {
        User user = getUserByEmail(email);
        Hibernate.initialize(user.getProfile());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getProfile().getLastname(), user.getProfile().getFirstname(), user.getProfile().getMiddleName());
        userDTO.setProfileImage(user.getProfile().getProfileImage());
        return userDTO;
    }

    @Override
    @Transactional
    public UserDTO getUser(String email) {
        User user = getUserByEmail(email);
        Hibernate.initialize(user.getProfile());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getProfile().getLastname(), user.getProfile().getFirstname(), user.getProfile().getMiddleName());
        userDTO.setProfileImage(user.getProfile().getProfileImage());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstname(user.getProfile().getFirstname());
        userDTO.setMiddleName(user.getProfile().getMiddleName());
        userDTO.setLastname(user.getProfile().getLastname());
        if (user.getProfile().getBirthdate() != null)
            userDTO.setBirthdate(user.getProfile().getBirthdate());
        userDTO.setNotes(user.getProfile().getNotes());
        userDTO.setPhoneNumber(user.getProfile().getPhoneNumber());
        userDTO.setViberLogin(user.getProfile().getViberLogin());
        userDTO.setTelegramLogin(user.getProfile().getTelegramLogin());
        userDTO.setBirthdate(user.getProfile().getBirthdate());
        userDTO.setUniqueId(user.getUniqueId());
        userDTO.setHasDebt(user.isHasDebt());
        List<Flat> flats = flatRepo.findAllByUserId(user.getId());
        if (!flats.isEmpty()) {
            List<FlatDTO> flatDTOS = new ArrayList<>();
            for (Flat flat : flats) {
                FlatDTO flatDTO = new FlatDTO();
                flatDTO.setId(flat.getId());
                flatDTO.setTitle(flat.getNumber());
                flatDTO.setNumber(flat.getNumber());
                flatDTO.setTotalSquare(flat.getTotalSquare());
                flatDTO.setFloor(flat.getFloor());
                flatDTO.setSection(flat.getSection());
                if (flat.getAccount() != null) {
                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setId(flat.getAccount().getId());
                    accountDTO.setUniqueNumber(flat.getAccount().getUniqueNumber());
                    flatDTO.setAccount(accountDTO);
                }
                BuildingDTO buildingDTO = new BuildingDTO();
                buildingDTO.setId(flat.getBuilding().getId());
                buildingDTO.setTitle(flat.getBuilding().getTitle());
                buildingDTO.setAddress(flat.getBuilding().getAddress());
                buildingDTO.setImage1(flat.getBuilding().getImage1());
                buildingDTO.setImage2(flat.getBuilding().getImage2());
                buildingDTO.setImage3(flat.getBuilding().getImage3());
                buildingDTO.setImage4(flat.getBuilding().getImage4());
                buildingDTO.setImage5(flat.getBuilding().getImage5());
                flatDTO.setBuilding(buildingDTO);
                flatDTOS.add(flatDTO);
            }
            userDTO.setFlats(flatDTOS);
        }
        return userDTO;
    }

    @Override
    public List<FlatDTO> getAllUserFlats(String userEmail) {
        User user = userRepo.getUserByEmail(userEmail);
        return flatRepo.findAllByUserId(user.getId())
                .stream()
                .map(flat -> new FlatDTO(flat.getId(), flat.getBuilding().getTitle(), flat.getNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException(String.format("User %s not found", email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }

}
