package kloud.backend.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class NamespaceServiceTest {
    @Resource
    private NamespaceService namespaceService;

    @Test
    void create() {
        String[] uids = {"23684279", "068492", "666", "54310"};

        for (String item : uids) {
            namespaceService.createUserNamespace(item);
            namespaceService.deleteUserNamespace(item);
        }

    }
}