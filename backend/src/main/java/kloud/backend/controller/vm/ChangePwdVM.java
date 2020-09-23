package kloud.backend.controller.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePwdVM {

    @NotNull
    private Long id;

    @NotNull
    private String oldPwd;

    @NotNull
    private String password;
}
