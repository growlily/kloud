package kloud.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class PodServiceTest {
    @Resource
    private PodService podService;

    @Test
    void listNamespace() {
        System.out.println(podService.listUser("default", ""));
    }

}