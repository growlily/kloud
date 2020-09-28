package kloud.backend.service.dto.podCreate;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PodCreateParam {
    @NotNull
    private String id;
    @NotNull
    private String course;
    @NotNull
    private PodParam param;

}

