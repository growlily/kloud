package kloud.backend.service;

import kloud.backend.entity.Course;
import kloud.backend.entity.UserCourse;
import kloud.backend.repository.CourseRepository;
import kloud.backend.repository.UserCourseRepository;
import kloud.backend.service.dto.UserCourseDTO;
import kloud.backend.service.dto.UserCourseDTO2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserCourseService {

    @Resource
    private UserCourseRepository userCourseRepository;

    @Resource
    private CourseRepository courseRepository;


    public List<UserCourseDTO> getAll(Map<String, String> params) {
        return userCourseRepository.selAllUsersByCourseId(Long.valueOf(params.get("courseId")));
    }

    public List<UserCourseDTO2> getAllNotInCourse(Map<String, String> params) {
        return userCourseRepository.selAllUsersNotInCourse(Long.valueOf(params.get("courseId")));
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public void deleteStudents(Map<String,Object> params) {
        ArrayList<Integer> list = (ArrayList<Integer>) params.get("list");
        List<Long> collect = list.stream().map(i -> Long.valueOf(i.longValue())).collect(Collectors.toList());
        Long courseId = Long.valueOf((String) params.get("courseId"));
        Course course = courseRepository.findById(courseId).get();
        course.setTotal(course.getTotal() - collect.size());
        courseRepository.save(course);
        userCourseRepository.deleteAllByCourseIdAndStudentIdIn(Long.valueOf((String) params.get("courseId")), collect);

    }

    @SuppressWarnings("unchecked")
    public void addStudents(Map<String,Object> params) {
        ArrayList<Integer> list = (ArrayList<Integer>) params.get("list");
        List<Long> collect = list.stream().map(i -> Long.valueOf(i.longValue())).collect(Collectors.toList());
        Long courseId = Long.valueOf((String) params.get("courseId"));
        Course course = courseRepository.findById(courseId).get();
        String createdBy =(String) params.get("realName");
        for(Long item : collect) {
            UserCourse userCourse = new UserCourse();
            userCourse.setCreatedBy(createdBy);
            userCourse.setCourseId(courseId);
            userCourse.setStudentId(item);
            userCourseRepository.save(userCourse);
        }
        course.setTotal(course.getTotal() + collect.size());
        courseRepository.save(course);
    }

}
