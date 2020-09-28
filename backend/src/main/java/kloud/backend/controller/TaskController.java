package kloud.backend.controller;

import kloud.backend.entity.Task;
import kloud.backend.service.TaskService;
import kloud.backend.service.dto.StudentTaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @CrossOrigin
    @PostMapping("/getAll")
    public ResponseEntity<List<Task>> getAllTaskByCourseId(@RequestBody Map<String, String> params) {
        List<Task> tasks = taskService.getAllTasksByCourseId(Long.valueOf(params.get("courseId")));
        return ResponseEntity.ok(tasks);
    }

    @CrossOrigin
    @GetMapping("/getOne")
    public ResponseEntity<Task> getOneTaskById(@RequestParam Long id) {
        return ResponseEntity.ok(taskService.getOneTaskById(id));
    }

    @CrossOrigin
    @PostMapping("/addTask")
    public ResponseEntity<Void> addTask(String taskName, String taskInfo,
                                     String fromTime, String endTime,
                                     String delayTime, MultipartFile file, String courseId) {
        taskService.addTask(taskName, taskInfo, fromTime, endTime, delayTime, file, courseId);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/addTaskWithoutResource")
    public ResponseEntity<Void> addTaskWithoutResource(@RequestBody Map<String, String> params) {
        taskService.addTask(params);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/deleteTask")
    public ResponseEntity<Void> deleteTask(@RequestBody Map<String, String> params) {
        taskService.deleteTaskById(Long.valueOf(params.get("id")));
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/updateTask")
    public ResponseEntity<Void> updateTask(String id, String taskName, String taskInfo,
                                           String fromTime, String endTime,
                                           String delayTime, MultipartFile file) {
        taskService.updateTask(id, taskName, taskInfo, fromTime, endTime, delayTime, file);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/updateTaskWithoutResource")
    public ResponseEntity<Void> updateTaskWithoutResource(@RequestBody Map<String, String> params) {
        taskService.updateTask(params);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @GetMapping("/getAllStudentTasks")
    public ResponseEntity<List<StudentTaskDTO>> getAllStudentTasks(@RequestParam Long id) {

        return ResponseEntity.ok(taskService.getAllStudentTasks(id));
    }

}
