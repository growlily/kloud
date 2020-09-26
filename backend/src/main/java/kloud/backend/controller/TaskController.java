package kloud.backend.controller;

import kloud.backend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @CrossOrigin
    @PostMapping("/addTask")
    public ResponseEntity<Void> addTask(String taskName, String taskInfo,
                                     String fromTime, String endTime,
                                     String delayTime, MultipartFile file) {
        System.out.println(taskName);
        System.out.println(taskInfo);
        System.out.println(fromTime);
        System.out.println(endTime);
        System.out.println(delayTime);
        System.out.println(file.getOriginalFilename());
        return null;
    }

    @CrossOrigin
    @PostMapping("/addTaskWithoutResource")
    public ResponseEntity<Void> addTaskWithoutResource(@RequestBody Map<String, String> params) {
        System.out.println(params.get("taskName"));
        return null;
    }

}
