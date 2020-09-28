package kloud.backend.service;


import kloud.backend.config.Constants;
import kloud.backend.entity.Course;
import kloud.backend.entity.Task;
import kloud.backend.entity.User;
import kloud.backend.repository.CourseRepository;
import kloud.backend.repository.TaskRepository;
import kloud.backend.repository.UserRepository;
import kloud.backend.util.MinioUtil;
import kloud.backend.util.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Resource
    private TaskRepository taskRepository;

    @Resource
    private CourseRepository courseRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private MinioUtil minioUtil;

    public List<Task> getAllTasksByCourseId(Long courseId) {
        return taskRepository.findAllByCourseId(courseId);
    }

    public void addTask(String taskName, String taskInfo,
                        String fromTime, String endTime,
                        String delayTime, MultipartFile file, String courseId) {
        String fileName = file.getOriginalFilename();
        Task task = addTask(taskName, taskInfo, fromTime, endTime, delayTime,
                 fileName,Long.valueOf(courseId));
        String taskId = task.getId().toString();
        String buckName = Constants.BUCKET_PREFIX + taskId;
        String minioFileName = taskId + fileName.substring(fileName.lastIndexOf('.'));
        String url = minioUtil.upload(buckName, file, minioFileName);
        task.setTaskResource(url);
        taskRepository.save(task);
    }

    public void addTask(Map<String, String> params) {
        addTask(params.get("taskName"), params.get("taskInfo"), params.get("fromTime"),
                params.get("endTime"), params.get("delayTime"), null, Long.valueOf(params.get("courseId")));
    }

    private Task addTask(String taskName, String taskInfo,
                         String fromTime, String endTime,
                         String delayTime, String taskResourceName,
                         Long courseId) {
        Task task = new Task();
        task.setTaskName(taskName);
        task.setTaskInfo(taskInfo);
        task.setFromTime(TimeUtils.parseTime(fromTime));
        task.setEndTime(TimeUtils.parseTime(endTime));
        if(delayTime == null || delayTime.length() == 0) {
            delayTime = endTime;
        }
        task.setDelayTime(TimeUtils.parseTime(delayTime));
        task.setCourseId(courseId);
        if(taskResourceName != null)task.setTaskResourceName(taskResourceName);
        task.setCreatedBy(userRepository.findOneById(courseRepository.findOneById(courseId).get().getTeacherId()).get().getRealName());
        return taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        minioUtil.removeBucket(Constants.BUCKET_PREFIX + id);
        taskRepository.deleteById(id);
    }

    public void updateTask(String id, String taskName, String taskInfo,
                           String fromTime, String endTime,
                           String delayTime, MultipartFile file) {
        Task task = taskRepository.findById(Long.valueOf(id)).get();
        String fileName = file.getOriginalFilename();
        String buckName = Constants.BUCKET_PREFIX + id;
        String originMinoFileName =
                id + task.getTaskResourceName().substring(task.getTaskResourceName().lastIndexOf('.'));
        String minioFileName = id + fileName.substring(fileName.lastIndexOf('.'));
        minioUtil.removeObject(buckName, originMinoFileName);
        String url = minioUtil.upload(buckName, file, minioFileName);
        task.setTaskName(taskName);
        task.setTaskInfo(taskInfo);
        task.setFromTime(TimeUtils.parseTime(fromTime));
        task.setEndTime(TimeUtils.parseTime(endTime));
        task.setDelayTime(TimeUtils.parseTime(delayTime));
        task.setTaskResource(url);
        task.setTaskResourceName(fileName);
        task.setLastModifiedBy(userRepository
                .findOneById(courseRepository.findOneById(task.getCourseId()).get().getTeacherId()).get().getRealName());
        taskRepository.save(task);
    }

    public void updateTask(Map<String, String> params) {
        String id = params.get("id");
        String taskName = params.get("taskName");
        String taskInfo = params.get("taskInfo");
        String fromTime = params.get("fromTime");
        String endTime = params.get("endTime");
        String delayTime = params.get("delayTime");
        Task task = taskRepository.findById(Long.valueOf(id)).get();
        task.setTaskName(taskName);
        task.setTaskInfo(taskInfo);
        task.setFromTime(TimeUtils.parseTime(fromTime));
        task.setEndTime(TimeUtils.parseTime(endTime));
        task.setDelayTime(TimeUtils.parseTime(delayTime));
        taskRepository.save(task);
    }

}
