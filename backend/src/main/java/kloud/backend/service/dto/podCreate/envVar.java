package kloud.backend.service.dto.podCreate;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class envVar {
    @NotNull
    private String name;
    private String value;
}
