package kloud.backend.service;

import kloud.backend.config.Constants;
import kloud.backend.repository.TaskHomeworkRepository;
import kloud.backend.util.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class TaskHomeworkService {

    @Resource
    private TaskHomeworkRepository taskHomeworkRepository;

    @Resource
    private MinioUtil minioUtil;

    public void uploadHomework(String userId, String login, String realName, String taskId, String taskName, MultipartFile file) {
        String originFileName = file.getOriginalFilename();
        String suffix = originFileName.substring(originFileName.lastIndexOf('.'));
        String fileName = login + '_' + realName + '_' + taskName + suffix;
        String buckName = Constants.BUCKET_PREFIX + taskId;
//        if()
    }

}
