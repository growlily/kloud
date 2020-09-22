package kloud.backend.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PodList;
import kloud.backend.entity.KPodInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PodService {
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

    public String log(String podName, String namespace) {
        CoreV1Api coreApi = new CoreV1Api();
        try {
            return coreApi.readNamespacedPodLog(podName, namespace, null, false, null, null, null, null, null, null, null);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String log(String podName) {
        return log(podName, "Default");
    }
}
