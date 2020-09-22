package kloud.backend.controller.node;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.openapi.models.V1NodeAddress;
import io.kubernetes.client.openapi.models.V1NodeList;
import kloud.backend.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/node")
public class NodeListController {
    @GetMapping("/list")
    public Result<List<NodeInfo>> nodeList() {
        CoreV1Api coreV1Api = new CoreV1Api();
        V1NodeList result = null;
        try {
            result = coreV1Api.listNode(null, null, null, null, null, null, null, null, null);

        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#listNode");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        assert result != null;
        List<NodeInfo> nodeInfoList = result.getItems().stream().map(NodeInfo::new).collect(Collectors.toList());
        return new Result<>(200, "", nodeInfoList);
    }
}

class NodeInfo {
    String hostname;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address;

    NodeInfo(V1Node node) {
        hostname = Objects.requireNonNull(node.getMetadata()).getName();
        V1NodeAddress addr = Objects.requireNonNull(Objects.requireNonNull(node.getStatus()).getAddresses()).get(0);
        this.address = addr.getAddress();
    }
}