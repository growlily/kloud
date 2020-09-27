package kloud.backend.service.dto;

import io.kubernetes.client.custom.NodeMetrics;
import io.kubernetes.client.openapi.models.V1Node;
import lombok.Data;

import java.util.Objects;

import static kloud.backend.config.Constants.*;

@Data
public class KNodeInfo {
    private String hostname;
    private int cpuCores;
    private int cpuPercent;
    private int memTotal;
    private int memUsage;
    private int memPercent;
    private String os;
    private String address;

    public KNodeInfo(V1Node node, NodeMetrics metrics) {
        hostname = Objects.requireNonNull(node.getMetadata()).getName();
        address = Objects.requireNonNull(Objects.requireNonNull(node.getStatus()).getAddresses()).get(0).getAddress();
        os = Objects.requireNonNull(node.getStatus().getNodeInfo()).getOsImage();

        double cpuTotal = node.getStatus().getCapacity().get(CPU).getNumber().doubleValue();
        double cpuUsage = metrics.getUsage().get(CPU).getNumber().doubleValue();
        cpuCores = (int) cpuTotal;
        cpuPercent = (int) (cpuUsage * 100 / cpuTotal);

        double memTotal = node.getStatus().getCapacity().get(MEMORY).getNumber().doubleValue();
        this.memTotal = (int) (memTotal / MiB);
        double memUsage = metrics.getUsage().get(MEMORY).getNumber().doubleValue();
        this.memUsage = (int) (memUsage / MiB);
        this.memPercent = (int) (memUsage * 100 / memTotal);
    }

}

