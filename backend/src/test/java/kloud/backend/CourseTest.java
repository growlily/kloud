package kloud.backend;

import kloud.backend.controller.vm.AddCourseVM;
import kloud.backend.entity.Course;
import kloud.backend.entity.Task;
import kloud.backend.repository.CourseRepository;
import kloud.backend.repository.TaskRepository;
import kloud.backend.service.CourseService;
import kloud.backend.util.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseTest {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TaskRepository taskRepository;

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

    /**
     * 测试课程添加任务
     */
    @Test
    public void taskAddTest() {
        Task task1 = new Task();
        task1.setTaskName("综合实践作品打包云项目");
        task1.setTaskInfo("完成专业方向实践云计算部分内容，提交设计的作品代码，设计文档，操作手册，如申请优秀答辩请准备PPT。");
        task1.setFromTime(TimeUtils.parseTime("2020-09-21 00:00:00"));
        task1.setEndTime(TimeUtils.parseTime("2020-09-30 08:00:00"));
        task1.setDelayTime(TimeUtils.parseTime("2020-10-07 00:00:00"));
        task1.setCourseId(1L);
        task1.setCreatedBy("测试人员");
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTaskName("大数据实验环境部署和数据分析");
        task2.setTaskInfo("提交综合实验报告");
        task2.setFromTime(TimeUtils.parseTime("2020-09-27 00:00:00"));
        task2.setEndTime(TimeUtils.parseTime("2020-09-30 08:00:00"));
        task2.setDelayTime(TimeUtils.parseTime("2020-10-01 00:00:00"));
        task2.setCourseId(1L);
        task2.setCreatedBy("测试人员");
        taskRepository.save(task2);
    }
}
