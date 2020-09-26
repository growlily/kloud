package kloud.backend.service.dto.podCreate;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PodParam {
    @NotNull
    private String image;
    private List<envVar> envVarList;
}
