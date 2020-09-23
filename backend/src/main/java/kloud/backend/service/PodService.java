package kloud.backend.service;

import com.google.common.io.ByteStreams;
import io.kubernetes.client.Exec;
import io.kubernetes.client.Metrics;
import io.kubernetes.client.custom.PodMetricsList;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import kloud.backend.entity.KPodInfo;
import kloud.backend.util.UserNSUtil;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        Metrics metrics = new Metrics();
        try {
            PodMetricsList podMetricsList = metrics.getPodMetrics(namespace);
            return podMetricsList.getItems().stream().map(podMetrics -> {
                V1Pod pod = detail(podMetrics.getMetadata().getName(), namespace);
                return new KPodInfo(pod, podMetrics);
            }).collect(Collectors.toList());

        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    private V1Pod detail(String podName, String namespace) {
        CoreV1Api api = new CoreV1Api();
        try {
            return api.readNamespacedPod(podName, namespace, null, null, null);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String log(String podName, String uid) throws ApiException {
        CoreV1Api coreApi = new CoreV1Api();
        String namespace = UserNSUtil.toNS(uid);
        return coreApi.readNamespacedPodLog(podName, namespace, null, false, null, null, null, null, null, null, null);
    }

    //create pod. return pod name if success, else null
    public String create(@NotNull String image, String uid) {
        CoreV1Api api = new CoreV1Api();
        String dnsLabel = image.toLowerCase().replace('/', '-').replace(':', '-');
        V1Container container = new V1Container().image(image).name(dnsLabel);

        V1Pod pod = new V1Pod();
        pod.spec(new V1PodSpec().containers(Collections.singletonList(container)));
        pod.metadata(new V1ObjectMeta().generateName(dnsLabel));
        String namespace = UserNSUtil.toNS(uid);
        try {
            V1Pod result = api.createNamespacedPod(namespace, pod, null, null, null);
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

    public boolean delete(String podName, String uid) {
        CoreV1Api api = new CoreV1Api();
        String namespace = UserNSUtil.toNS(uid);
        try {
            api.deleteNamespacedPod(podName, namespace, null, null, null, null, null, null);
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


    public void anoexec(String podName, String namespace, String command)
            throws IOException, ApiException, InterruptedException {

        Exec exec = new Exec();
        boolean tty = System.console() != null;

        // final Process proc = exec.exec("default", "nginx-4217019353-k5sn9", new String[]
        //   {"sh", "-c", "echo foo"}, true, tty);
        final Process proc =
                exec.exec(namespace, podName, new String[]{"ls", "-al"}, true, tty);

        Thread in =
                new Thread(
                        () -> {
                            try {
                                ByteStreams.copy(System.in, proc.getOutputStream());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
        in.start();

        Thread out =
                new Thread(
                        () -> {
                            try {
                                ByteStreams.copy(proc.getInputStream(), System.out);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
        out.start();

        proc.waitFor();

        // wait for any last output; no need to wait for input thread
        out.join();

        proc.destroy();

        System.exit(proc.exitValue());
    }


}

