package kloud.backend.service;

import kloud.backend.entity.User;
import kloud.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * The very first version of log-in service.
     */
    public Optional<User> login(String username, String password) {
        return userRepository.findOneByLoginAndPassword(username, password);
    }
}
