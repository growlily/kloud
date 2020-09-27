package kloud.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class NodeServiceTest {
    @Resource
    private NodeService nodeService;

    @Test
    void listWithMetrics() {
        System.out.println(nodeService.list());
        System.out.println(nodeService.list());
    }

    @Test
    void detail() {

    }
}
