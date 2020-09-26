package kloud.backend.service;

import io.kubernetes.client.Metrics;
import io.kubernetes.client.custom.PodMetrics;
import io.kubernetes.client.custom.PodMetricsList;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import kloud.backend.service.dto.KPodInfo;
import kloud.backend.service.dto.podCreate.PodCreateParam;
import kloud.backend.util.UserNSUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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


    public List<KPodInfo> listUser(String uid) {
        String namespace = UserNSUtil.toNS(uid);
        CoreV1Api api = new CoreV1Api();
        try {
            return api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null)
                    .getItems().stream().map(pod ->
                            getPodMetrics(Objects.requireNonNull(pod.getMetadata()).getName(), namespace)
                                    .map(podMetrics -> new KPodInfo(pod, podMetrics))
                                    .orElseGet(() -> new KPodInfo(pod))
                    ).collect(Collectors.toList());
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Optional<PodMetrics> getPodMetrics(String podName, String namespace) {
        Metrics metrics = new Metrics();
        try {
            PodMetricsList podMetricsList = metrics.getPodMetrics(namespace);
            for (PodMetrics podMetrics : podMetricsList.getItems()) {
                if (Objects.equals(podMetrics.getMetadata().getName(), podName)) {
                    return Optional.of(podMetrics);
                }
            }

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public String log(String podName, String uid) throws ApiException {
        CoreV1Api coreApi = new CoreV1Api();
        String namespace = UserNSUtil.toNS(uid);
        return coreApi.readNamespacedPodLog(podName, namespace, null, false, null, null, null, null, null, null, null);
    }

    //create pod. return status code
    public int create(PodCreateParam param) {
        CoreV1Api api = new CoreV1Api();
        String image = param.getParam().getImage();
        String dnsLabel;
        if (param.getParam().getPrefix().equals(""))
            dnsLabel = image.toLowerCase().replace('/', '-').replace(':', '-');
        else {
            dnsLabel = param.getParam().getPrefix();
        }
        List<V1EnvVar> envVarList = param.getParam().getEnvVarList()
                .stream().filter(envVar -> envVar.getName() != null && !envVar.getName().equals(""))
                .map(envVar -> {
                    V1EnvVar v1EnvVar = new V1EnvVar().name(envVar.getName());
                    if (envVar.getValue() != null && !envVar.getValue().equals(""))
                        v1EnvVar.value(envVar.getValue());
                    return v1EnvVar;
                }).collect(Collectors.toList());
        V1Container container = new V1Container()
                .image(image).name(dnsLabel)
                .stdin(true).tty(true)
                .env(envVarList);

        V1Pod pod = new V1Pod();
        pod.spec(new V1PodSpec().containers(Collections.singletonList(container)));
        pod.metadata(new V1ObjectMeta().generateName(dnsLabel + "-"));
        String namespace = UserNSUtil.toNS(param.getId());
        try {
            V1Pod result = api.createNamespacedPod(namespace, pod, null, null, null);
            return 200;
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createNamespacedPod");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return e.getCode();
        }
    }

    public String delete(String podName, String uid) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        String namespace = UserNSUtil.toNS(uid);

        api.deleteNamespacedPod(podName, namespace, null, null, null, null, null, null);
        return "success";
    }
}

