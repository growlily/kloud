package kloud.backend.service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1ResourceQuota;
import io.kubernetes.client.openapi.models.V1ResourceQuotaSpec;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NamespaceService {
    public void createNamespace(String name) {
        CoreV1Api api = new CoreV1Api();
        V1Namespace namespace = new V1Namespace();
        namespace.metadata(new V1ObjectMeta().name(name));
        try {
            api.createNamespace(namespace, null, null, null);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#createNamespace");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());

        }
    }

    public void ensureNamespace(String name) {
        CoreV1Api api = new CoreV1Api();
        try {
            api.readNamespace(name, null, null, null);
        } catch (ApiException e) {
            createNamespace(name);
            while (true) try {
                api.readNamespace(name, null, null, null);
                break;
            } catch (ApiException ignored) {

            }
        }

    }

    public Optional<V1ResourceQuota> getQuota(String name) {
        CoreV1Api api = new CoreV1Api();
        try {
            return api.listNamespacedResourceQuota(name, null, null, null, null, null, null, null, null, null)
                    .getItems().stream().findFirst();
        } catch (ApiException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void createQuota(String name) throws ApiException {
        CoreV1Api api = new CoreV1Api();
        api.createNamespacedResourceQuota(name,
                new V1ResourceQuota().spec(new V1ResourceQuotaSpec()), null, null, null);

    }

    public void deleteNamespace(String name) {
        CoreV1Api api = new CoreV1Api();
        try {
            api.deleteNamespace(name, null, null, null, null, null, null);
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#deleteNamespace");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());

        }
    }
}

