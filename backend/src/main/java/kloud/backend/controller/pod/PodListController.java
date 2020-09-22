package kloud.backend.controller.pod;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import kloud.backend.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pod")
public class PodListController {
    @GetMapping("/list")
    public Result<List<PodInfo>> list() {
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
        List<PodInfo> podList = list.getItems().stream().map(PodInfo::new).collect(Collectors.toList());
        return new Result<>(200, "", podList);
    }
}


class PodInfo {
    String name;

    String image;
    String address;

    public PodInfo(V1Pod v1Pod) {
        name = Objects.requireNonNull(v1Pod.getMetadata()).getName();
        image = Objects.requireNonNull(v1Pod.getSpec()).getContainers().get(0).getImage();
    }

    public String getName() {
        return name;
    }


    public String getImage() {
        return image;
    }


    public String getAddress() {
        return address;
    }

}