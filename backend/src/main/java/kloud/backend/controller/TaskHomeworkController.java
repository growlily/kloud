package kloud.backend.controller;

import kloud.backend.service.TaskHomeworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/taskHomework")
public class TaskHomeworkController {

    @Resource
    private TaskHomeworkService taskHomeworkService;

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<Void> uploadHomework(String userId, String login, String realName, String taskId, String taskName, MultipartFile file) {
        taskHomeworkService.upload(userId, login, realName, taskId, taskName, file);
        return ResponseEntity.ok().build();
    }


    @CrossOrigin
    @GetMapping("/isSubmited")
    public ResponseEntity<Boolean> isSubmitted(@RequestParam Long userId, @RequestParam Long taskId) {

        return ResponseEntity.ok(taskHomeworkService.isSubmitted(userId, taskId));
    }

}
