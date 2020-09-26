package kloud.backend.controller;

import kloud.backend.controller.vm.AddCourseVM;
import kloud.backend.entity.Course;
import kloud.backend.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @CrossOrigin
    @PostMapping("/addCourse")
    public ResponseEntity<Void> addCourse(@Valid @RequestBody AddCourseVM addCourseVM) {
        courseService.addCourse(addCourseVM);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/listCourses")
    public ResponseEntity<List<Course>> listCourses(@RequestBody Map<String, String> params) {
        String teacherId = params.get("id");
        return ResponseEntity.ok(courseService.getAllCoursesByTeacherId(Long.valueOf(Long.valueOf(teacherId))));
    }

    @CrossOrigin
    @PostMapping("/deleteCourse")
    public ResponseEntity<Void> deleteCourse(@RequestBody Map<String, String> params) {
        courseService.deleteCourseById(Long.valueOf(params.get("id")), Long.valueOf(params.get("teacherId")));
        return ResponseEntity.ok().build();
    }

}
