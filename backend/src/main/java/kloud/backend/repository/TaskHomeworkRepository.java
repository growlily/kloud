package kloud.backend.repository;

import kloud.backend.entity.TaskHomework;
import kloud.backend.service.dto.HomeworkFileDTO;
import kloud.backend.service.dto.UserCourseDTO2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskHomeworkRepository extends JpaRepository<TaskHomework, Long> {

    Optional<TaskHomework> findByUserIdAndTaskId(Long userId, Long taskId);

    @Query(value = "select th.url as url, th.fileName as fileName from TaskHomework th where th.taskId = ?1")
    List<HomeworkFileDTO> getAllUrlByTaskId(Long taskId);

    long countAllByTaskId(Long id);

    @Query(value = "select u.id as id, u.login as login, u.real_name as realName from saas_user u where u.id not in (select th.user_id from saas_task_homework th where th.task_id = ?1) and u.id in (select uc.student_id from saas_users_courses uc where uc.course_id = ?2)", nativeQuery = true)
    List<UserCourseDTO2> getAllStudentNotSubmitted(Long taskId, Long courseId);

    void deleteAllByTaskId(Long taskId);
}
