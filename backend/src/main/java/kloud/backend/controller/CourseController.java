package kloud.backend.controller;

import kloud.backend.controller.vm.AddCourseVM;
import kloud.backend.entity.Course;
import kloud.backend.repository.CourseRepository;
import kloud.backend.service.CourseService;
import kloud.backend.service.dto.StudentCourseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @Resource
    private CourseRepository courseRepository;

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

    @CrossOrigin
    @PostMapping("/getCourse")
    public ResponseEntity<Course> getCourse(@RequestBody Map<String, String> params) {
        Optional<Course> course = courseRepository.findById(Long.valueOf(params.get("id")));
        return course.map(c -> new ResponseEntity<>(c, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @CrossOrigin
    @GetMapping("/getStudentCourses")
    public ResponseEntity<List<StudentCourseDTO>> getCoursesByStudentId(@RequestParam Long id) {

        return ResponseEntity.ok(courseService.getCoursesByStudentId(id));
    }

}
