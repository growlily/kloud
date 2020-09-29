package kloud.backend;

import kloud.backend.service.TaskHomeworkService;
import kloud.backend.service.dto.HomeworkFileDTO;
import kloud.backend.service.dto.UserCourseDTO2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TaskHomeworkTest {

    @Autowired
    private TaskHomeworkService taskHomeworkService;

    @Test
    public void test1() {
        List<HomeworkFileDTO> urls = taskHomeworkService.getAllUrlByTaskId(1L);
        System.out.println(urls);
    }

    @Test
    public void test2() {
        List<UserCourseDTO2> allStudentNotSubmitted = taskHomeworkService.getAllStudentNotSubmitted(1L, 1L);
        System.out.println(allStudentNotSubmitted);
    }

}
