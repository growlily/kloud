package kloud.backend.controller.vm;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangeInfoVM {
    @NotNull
    private Long id;
    @Size(max=50)
    private String realName;
    @Email
    @Size(min = 5, max = 254)
    private String email;
}
