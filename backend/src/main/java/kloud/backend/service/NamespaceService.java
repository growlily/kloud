package kloud.backend.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import kloud.backend.util.UserNSUtil;
import org.springframework.stereotype.Service;

@Service
public class NamespaceService {
    public void createUserNamespace(String uid) {
        CoreV1Api api = new CoreV1Api();
        V1Namespace namespace = new V1Namespace();
        namespace.metadata(new V1ObjectMeta().name(UserNSUtil.toNS(uid)));
        try {
            api.createNamespace(namespace, null, null, null);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createNamespace");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());

        }
    }

    public void deleteUserNamespace(String uid) {
        CoreV1Api api = new CoreV1Api();
        try {
            api.deleteNamespace(UserNSUtil.toNS(uid), null, null, null, null, null, null);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#deleteNamespace");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());

        }
    }
}

