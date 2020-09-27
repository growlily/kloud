package kloud.backend.service;

import io.kubernetes.client.Metrics;
import io.kubernetes.client.custom.NodeMetricsList;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;
import kloud.backend.service.dto.KNodeInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeService {
    /**
     * 列出所有node信息
     * @return  包含各种信息的KNode类型实体
     */
    public List<KNodeInfo> list() {
        Metrics metrics = new Metrics();
        try {
            NodeMetricsList nodeMetricsList = metrics.getNodeMetrics();
            return nodeMetricsList.getItems().stream().map(nodeMetrics -> {
                V1Node node = detail(nodeMetrics.getMetadata().getName());
                return new KNodeInfo(node, nodeMetrics);
            }).collect(Collectors.toList());
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    V1Node detail(String name) {
        CoreV1Api api = new CoreV1Api();
        try {
            return api.readNode(name, null, null, null);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#readNode");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return null;
        }
    }

}
