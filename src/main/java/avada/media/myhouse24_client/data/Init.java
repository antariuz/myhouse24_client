package avada.media.myhouse24_client.data;

import avada.media.myhouse24_client.model.Profile;
import avada.media.myhouse24_client.model.Status;
import avada.media.myhouse24_client.model.User;
import avada.media.myhouse24_client.repo.ProfileRepo;
import avada.media.myhouse24_client.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class Init implements CommandLineRunner {

    private final UserRepo userRepo;
    private final ProfileRepo profileRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Initialization checks");
        log.info("Checking for the testing user");
        if (!userRepo.existsByEmail("test@gmail.com")) {
            log.warn("Testing user have not been found");
            log.info("Creating user for testing purposes");
            User user = new User();
            Profile profile = new Profile();
            user.setUniqueId("test");
            user.setEmail("test@gmail.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setStatus(Status.NEW);
            profile.setFirstname("Bob");
            profile.setMiddleName("SquarePants");
            profile.setLastname("Sponge");
            profile.setPhoneNumber("+380677777777");
            profileRepo.save(profile);
            user.setProfile(profile);
            userRepo.save(user);
            log.info("Testing user has been successfully created");
            log.info("Login: test@gmail.com");
            log.info("Password: password");
        } else log.info("Testing user has been found");
        log.info("Credentials for testing purposes:");
        log.info("Login: test@gmail.com");
        log.info("Password: password");
    }

}
