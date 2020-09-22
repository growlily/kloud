package kloud.backend.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NodeList;
import kloud.backend.entity.KNodeInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeService {
    public List<KNodeInfo> list() {
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
        return result.getItems().stream().map(KNodeInfo::new).collect(Collectors.toList());
    }
}
