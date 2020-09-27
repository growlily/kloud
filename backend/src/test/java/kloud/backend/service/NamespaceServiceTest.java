package kloud.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class NamespaceServiceTest {
    @Resource
    private NamespaceService namespaceService;

    @Test
    void get() {
        System.out.println(namespaceService.getQuota("kusr17373189"));
    }
}