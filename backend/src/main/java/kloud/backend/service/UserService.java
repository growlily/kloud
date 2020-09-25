package kloud.backend.service;

import kloud.backend.controller.vm.ChangeInfoVM;
import kloud.backend.controller.vm.ChangePwdVM;
import kloud.backend.entity.User;
import kloud.backend.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * The very first version of log-in service.
     */
    @Cacheable(value = "users")
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

}
