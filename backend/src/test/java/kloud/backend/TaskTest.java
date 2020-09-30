package kloud.backend;

import kloud.backend.entity.Task;
import kloud.backend.repository.TaskRepository;
import kloud.backend.service.dto.StudentTaskDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TaskTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void test() {
        List<StudentTaskDTO> tasks = taskRepository.getAllTasksByStudentId(1L);
        System.out.println(tasks.get(0).getCourseName());
        System.out.println(tasks.get(0).getTaskName());
        System.out.println(tasks.get(0).getFromTime());
        System.out.println(tasks.get(0).getEndTime());
    }

    @Test
    public void test1() {
        Task one = taskRepository.findById(1L).get();
        System.out.println(one);
    }
}
