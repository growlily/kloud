package kloud.backend.controller;

import kloud.backend.service.TaskHomeworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/taskHomework")
public class TaskHomeworkController {

    @Resource
    private TaskHomeworkService taskHomeworkService;

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<Void> uploadHomework(String userId, String login, String realName, String taskId, String taskName, MultipartFile file) {
        return null;
    }

}
