package kloud.backend;

import kloud.backend.controller.vm.AddCourseVM;
import kloud.backend.entity.Course;
import kloud.backend.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseTest {

    @Autowired
    CourseService courseService;

    @Test
    public void addTest() {
        AddCourseVM addCourseVM = new AddCourseVM();
        addCourseVM.setId(4L);
        addCourseVM.setCourseName("大数据技术综合实验");
        addCourseVM.setRealName("人民教师");
        courseService.addCourse(addCourseVM);
    }

    @Test
    public void addTest2() {
        AddCourseVM addCourseVM = new AddCourseVM();
        addCourseVM.setId(4L);
        addCourseVM.setCourseName("软件过程与质量");
        addCourseVM.setRealName("人民教师");
        courseService.addCourse(addCourseVM);
    }

    @Test
    public void listAllTest() {
        List<Course> list = courseService.getAllCoursesByTeacherId(4L);
        System.out.println(list);

    }

}
