package tn.ensit.userservice.user.services;

import org.springframework.stereotype.Service;
import tn.ensit.userservice.profile.entities.Profile;
import tn.ensit.userservice.profile.services.ProfileService;
import tn.ensit.userservice.user.entities.User;
import tn.ensit.userservice.user.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

// This annotation is required to declare a Service
@Service
public class UserService {

    // We are injecting the repository using the constructor (constructor based injection)
    // We don't need to initialize (or create) the repo ourselves, the Spring Framework will do it for us
    // Optionally we could have added an annotation @Autowired to specifically ask Spring to inject this field
    // But we didn't need it because Spring Framework is smart
    private final UserRepository userRepository;

    private final ProfileService profileService;

    public UserService(UserRepository repository, ProfileService profileService) {
        this.userRepository = repository;
        this.profileService = profileService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getOneUser(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        var createdUser = userRepository.save(user);
        var bio = "";
        Profile profile = new Profile(createdUser, bio);
        profileService.createProfile(profile);
        return createdUser;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
