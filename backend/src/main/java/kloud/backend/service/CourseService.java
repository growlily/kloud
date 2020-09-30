package kloud.backend.service;

import kloud.backend.controller.vm.AddCourseVM;
import kloud.backend.entity.Course;
import kloud.backend.entity.Task;
import kloud.backend.repository.CourseRepository;
import kloud.backend.repository.TaskRepository;
import kloud.backend.repository.UserCourseRepository;
import kloud.backend.service.dto.StudentCourseDTO;
import kloud.backend.util.TimeUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CourseService {

    @Resource
    private CourseRepository courseRepository;

    @Resource
    private UserCourseRepository userCourseRepository;

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private TaskService taskService;

//    @CachePut(value = "courselist", key = "#addCourseVM.id")
    public List<Course> addCourse(@NotNull AddCourseVM addCourseVM) {
        Course course = new Course();
        course.setCourseName(addCourseVM.getCourseName());
        course.setTeacherId(addCourseVM.getId());
        course.setCreatedBy(addCourseVM.getRealName());
        course.setSemester(TimeUtils.getCourseSemester());
        courseRepository.save(course);
        return courseRepository.findAllByTeacherId(addCourseVM.getId());
    }

//    @Cacheable(value = "courselist", key = "#teacherId")
    public List<Course> getAllCoursesByTeacherId(Long teacherId) {

        return courseRepository.findAllByTeacherId(teacherId);
    }

//    @CacheEvict(value = "courselist", key = "#teacherId")
    public void deleteCourseById(Long id, Long teacherId) {
        Course course = courseRepository.findById(id).get();
        List<Task> tasks = taskRepository.findAllByCourseId(course.getId());
        for(Task task : tasks) {
            taskService.deleteTaskById(task.getId());
        }
        userCourseRepository.deleteAllByCourseId(course.getId());
        courseRepository.deleteById(id);
    }


    public List<StudentCourseDTO> getCoursesByStudentId(Long id) {
        return courseRepository.getCoursesByStudentId(id);
    }

}
