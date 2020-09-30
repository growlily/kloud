package kloud.backend.service;

import kloud.backend.controller.vm.ChangeInfoVM;
import kloud.backend.controller.vm.ChangePwdVM;
import kloud.backend.entity.User;
import kloud.backend.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private NamespaceService namespaceService;

    /**
     * The very first version of log-in service.
     */
    public Optional<User> login(String username, String password) {
        return userRepository.findOneByLoginAndPassword(username, password);
    }

    @Transactional
    public Optional<User> changeInfo(ChangeInfoVM changeInfoVM) {
        return Optional.of(userRepository
                .findOneById(changeInfoVM.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setRealName(changeInfoVM.getRealName());
                    user.setEmail(changeInfoVM.getEmail());
                    user.setLastModifiedBy(user.getRealName());
                    userRepository.save(user);
                    return user;
                });
    }

    @Transactional
    public boolean changePwd(ChangePwdVM changePwdVM) {
        return userRepository.findOneByIdAndPassword(changePwdVM.getId(), changePwdVM.getOldPwd())
                .map(user -> {
                    user.setPassword(changePwdVM.getPassword());
                    user.setLastModifiedBy(user.getRealName());
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void addUser(Map<String, String> map) {
        User user = new User();
        user.setLogin(map.get("login"));
        user.setPassword(map.get("password"));
        user.setRealName(map.get("realName"));
        user.setEmail(map.get("email"));
        user.setUserType(Integer.valueOf(map.get("userType")));
        user.setCreatedBy(map.get("createdBy"));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void resetPassword(Long id) {
        User user = userRepository.findOneById(id).get();
        user.setPassword("12345678");
        userRepository.save(user);
    }

}

