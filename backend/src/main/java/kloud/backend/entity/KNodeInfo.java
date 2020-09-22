package kloud.backend.entity;

import io.kubernetes.client.openapi.models.V1Node;
import lombok.Data;

import java.util.Objects;

@Data
public class KNodeInfo {
    private String hostname;
    private String address;
    private String os;

    public KNodeInfo(V1Node node) {
        hostname = Objects.requireNonNull(node.getMetadata()).getName();
        address = Objects.requireNonNull(Objects.requireNonNull(node.getStatus()).getAddresses()).get(0).getAddress();
        os = Objects.requireNonNull(node.getStatus().getNodeInfo()).getOsImage();

    }
}

