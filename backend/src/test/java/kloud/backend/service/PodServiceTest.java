package kloud.backend.service;

import io.kubernetes.client.openapi.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class PodServiceTest {
    @Resource
    private PodService podService;

    @Test
    void exec() throws InterruptedException, ApiException, IOException {
        podService.anoexec("redis-alpine4gcxl", "default", "ls -al");
    }

    @Test
    void listNamespace() {
        System.out.println(podService.listUser("default"));
    }

}