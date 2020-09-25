package kloud.backend.service.dto;

import io.kubernetes.client.custom.PodMetrics;
import io.kubernetes.client.openapi.models.V1Pod;
import lombok.Data;

import java.util.Objects;

@Data
public class KPodInfo {
    private static final String CPU = "cpu";
    private static final String MEMORY = "memory";
    private static final int MB = 1024 * 1024;

    private String name;
    private String image;
    private String status;
    //    private int cpuUsage;
    private int memUsage;

    public KPodInfo(V1Pod v1Pod) {
        name = Objects.requireNonNull(v1Pod.getMetadata()).getName();
        image = Objects.requireNonNull(v1Pod.getSpec()).getContainers().get(0).getImage();
        status = Objects.requireNonNull(v1Pod.getStatus()).getPhase();
    }

    public KPodInfo(V1Pod v1Pod, PodMetrics metrics) {
        name = Objects.requireNonNull(v1Pod.getMetadata()).getName();
        image = Objects.requireNonNull(v1Pod.getSpec()).getContainers().get(0).getImage();
        status = Objects.requireNonNull(v1Pod.getStatus()).getPhase();
        if (status != null && status.equals("Running")) {
//            cpuUsage = (int) metrics.getContainers().get(0).getUsage().get(CPU).getNumber().doubleValue();
            double memUsage = metrics.getContainers().get(0).getUsage().get(MEMORY).getNumber().doubleValue();
            this.memUsage = (int) (memUsage / MB);
        }
    }

}
