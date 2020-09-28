package kloud.backend.controller;

import kloud.backend.service.UserCourseService;
import kloud.backend.service.dto.UserCourseDTO;
import kloud.backend.service.dto.UserCourseDTO2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class UserCourseController {

    @Resource
    private UserCourseService userCourseService;


    @CrossOrigin
    @PostMapping("/getAll")
    public ResponseEntity<List<UserCourseDTO>> getAllByCourseId(@RequestBody Map<String, String> params) {

        return ResponseEntity.ok(userCourseService.getAll(params));
    }

    @CrossOrigin
    @PostMapping("/getAllNotInCourse")
    public ResponseEntity<List<UserCourseDTO2>> getAllNotInCourse(@RequestBody Map<String, String> params) {

        return ResponseEntity.ok(userCourseService.getAllNotInCourse(params));
    }

    @CrossOrigin
    @PostMapping("/deleteStudent")
    public ResponseEntity<Void> deleteStudents(@RequestBody Map<String,Object> params) {
        userCourseService.deleteStudents(params);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/addStudent")
    public ResponseEntity<Void> addStudents(@RequestBody Map<String,Object> params) {
        userCourseService.addStudents(params);
        return ResponseEntity.ok().build();
    }

}
