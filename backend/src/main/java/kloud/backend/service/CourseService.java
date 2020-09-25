package kloud.backend.service;

import kloud.backend.controller.vm.AddCourseVM;
import kloud.backend.entity.Course;
import kloud.backend.repository.CourseRepository;
import kloud.backend.util.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CourseService {

    @Resource
    private CourseRepository courseRepository;

    public void addCourse(@NotNull AddCourseVM addCourseVM) {
        Course course = new Course();
        course.setCourseName(addCourseVM.getCourseName());
        course.setTeacherId(addCourseVM.getId());
        course.setCreatedBy(addCourseVM.getRealName());
        course.setSemester(TimeUtils.getCourseSemester());
        courseRepository.save(course);
    }

    public List<Course> getAllCoursesByTeacherId(Long teacherId) {
        return courseRepository.findAllByTeacherId(teacherId);
    }


}
