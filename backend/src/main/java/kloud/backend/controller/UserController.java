package kloud.backend.controller;

import kloud.backend.controller.vm.ChangeInfoVM;
import kloud.backend.controller.vm.ChangePwdVM;
import kloud.backend.controller.vm.LoginVM;
import kloud.backend.entity.User;
import kloud.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserService userService;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginVM loginVM) {
        Optional<User> login = userService.login(loginVM.getUsername(), loginVM.getPassword());
        return login.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @CrossOrigin
    @PostMapping("/change/info")
    public ResponseEntity<User> changeInfo(@Valid @RequestBody ChangeInfoVM changeInfoVM) {
        Optional<User> user = userService.changeInfo(changeInfoVM);
        return user.map(u1 -> new ResponseEntity<>(u1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    @CrossOrigin
    @PostMapping("/change/pwd")
    public ResponseEntity<Void> changePwd(@Valid @RequestBody ChangePwdVM changePwdVM) {
        return userService.changePwd(changePwdVM) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


}
