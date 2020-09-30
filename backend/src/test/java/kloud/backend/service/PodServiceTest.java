package kloud.backend.service;

import kloud.backend.service.dto.KPodInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class PodServiceTest {
    @Resource
    private PodService podService;

    @Test
    void listNamespace() {
        System.out.println(podService.listUser("default", ""));
    }

    @Test
    public void test1() {
        List<KPodInfo> kPodInfos = podService.listUser("2020", "1");
        System.out.println(kPodInfos);
    }

}
