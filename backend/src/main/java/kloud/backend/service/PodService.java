package kloud.backend.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import kloud.backend.entity.KPodInfo;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PodService {
    private final String DEFAULT = "default";
    public List<KPodInfo> listAll() {
        // the CoreV1Api loads default api-client from global configuration.
        CoreV1Api api = new CoreV1Api();

        // invokes the CoreV1Api client
        V1PodList list = null;
        try {
            list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        assert list != null;
        return list.getItems().stream().map(KPodInfo::new).collect(Collectors.toList());
    }

    public List<KPodInfo> listNamespace(String namespace) {
        // the CoreV1Api loads default api-client from global configuration.
        CoreV1Api api = new CoreV1Api();

        // invokes the CoreV1Api client
        V1PodList list = null;
        try {
            list = api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        assert list != null;
        return list.getItems().stream().map(KPodInfo::new).collect(Collectors.toList());
    }

    public String log(String podName, String namespace) throws ApiException {
        CoreV1Api coreApi = new CoreV1Api();

        return coreApi.readNamespacedPodLog(podName, namespace, null, false, null, null, null, null, null, null, null);

    }

    public String log(String podName) throws ApiException {
        return log(podName, DEFAULT);
    }

    //create pod. return pod name if success, else null
    public String create(@NotNull String image) {
        CoreV1Api api = new CoreV1Api();
        String dnsLabel = image.toLowerCase().replace('/', '-').replace(':', '-');
        V1Container container = new V1Container().image(image).name(dnsLabel);

        V1Pod pod = new V1Pod();
        pod.spec(new V1PodSpec().containers(Collections.singletonList(container)));
        pod.metadata(new V1ObjectMeta().generateName(dnsLabel));
        try {
            V1Pod result = api.createNamespacedPod(DEFAULT, pod, null, null, null);
            return Objects.requireNonNull(result.getMetadata()).getName();
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createNamespacedPod");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(String podName) {
        CoreV1Api api = new CoreV1Api();
        try {
            api.deleteNamespacedPod(podName, DEFAULT, null, null, null, null, null, null);
            return true;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#deleteNamespacedPod");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return false;
        }
    }
}
