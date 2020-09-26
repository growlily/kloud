package kloud.backend.service;

import kloud.backend.entity.Course;
import kloud.backend.entity.Task;
import kloud.backend.repository.CourseRepository;
import kloud.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskService {

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private CourseRepository courseRepository;

    public void addTask(Task task, Long courseId) {


    }

}
