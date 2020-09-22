package kloud.backend.entity;

import io.kubernetes.client.openapi.models.V1Pod;
import lombok.Data;

import java.util.Objects;

@Data
public class KPodInfo {
    String name;
    String image;
    String status;
//    V1ContainerState st;

    public KPodInfo(V1Pod v1Pod) {
        name = Objects.requireNonNull(v1Pod.getMetadata()).getName();
        image = Objects.requireNonNull(v1Pod.getSpec()).getContainers().get(0).getImage();
        status = Objects.requireNonNull(v1Pod.getStatus()).getPhase();
//        st = v1Pod.getStatus().getContainerStatuses().get(0).getState();
    }

}