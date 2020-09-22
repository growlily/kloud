package kloud.backend.util;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;

@Component
class KubeClientInit {
    @PostConstruct
    static void kubeClientInit() {
        ApiClient client = null;
        // loading the in-cluster config, including:
        //   1. service-account CA
        //   2. service-account bearer-token
        //   3. service-account namespace
        //   4. master endpoints(ip, port) from pre-set environment variables
        try {
            System.out.println("Try to init k8s client using in-cluster config");
            client = ClientBuilder.cluster().build();
        } catch (Exception ignored) {
            System.out.println("Try to init k8s client using user provided config");

            // file path to your KubeConfig
            String kubeConfigPath = System.getProperty("user.home") + "\\.kube\\config";

            // loading the out-of-cluster config, a kubeconfig from file-system
            try {
                client =
                        ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
        if (client == null) {
            System.out.println("K8s client init failed");
            System.exit(-1);
        }
        System.out.println("K8s client init succeeded");

        // set the global default api-client to the in-cluster one from above

        Configuration.setDefaultApiClient(client);
    }
}
