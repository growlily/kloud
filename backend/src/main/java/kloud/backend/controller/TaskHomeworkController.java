package kloud.backend.controller;

import kloud.backend.service.TaskHomeworkService;
import kloud.backend.service.dto.HomeworkFileDTO;
import kloud.backend.service.dto.HomeworkStatistics;
import kloud.backend.service.dto.UserCourseDTO2;
import kloud.backend.util.FileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    @GetMapping("/isSubmitted")
    public ResponseEntity<Boolean> isSubmitted(@RequestParam Long userId, @RequestParam Long taskId) {

        return ResponseEntity.ok(taskHomeworkService.isSubmitted(userId, taskId));
    }

    @CrossOrigin
    @GetMapping("/downloadHomework")
    public void getAllUrlByTaskId(@RequestParam Long taskId, HttpServletResponse response) {
        List<HomeworkFileDTO> homeworkFiles = taskHomeworkService.getAllUrlByTaskId(taskId);
        if(!homeworkFiles.isEmpty())FileUtil.urldownloadZip(homeworkFiles, response);
    }

    @CrossOrigin
    @GetMapping("/getHomeworkStatistics")
    public ResponseEntity<HomeworkStatistics> getHomeworkStatistics(@RequestParam Long taskId, @RequestParam Long courseId) {
        return ResponseEntity.ok(taskHomeworkService.getHomeworkStatistics(taskId, courseId));
    }

    @CrossOrigin
    @GetMapping("/getAllNotSubmitted")
    public ResponseEntity<List<UserCourseDTO2>> getAllStudentNotSubmitted(@RequestParam Long taskId, @RequestParam Long courseId) {
        return ResponseEntity.ok(taskHomeworkService.getAllStudentNotSubmitted(taskId, courseId));
    }

}
