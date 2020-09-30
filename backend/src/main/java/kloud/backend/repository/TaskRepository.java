package kloud.backend.repository;

import kloud.backend.entity.Task;
import kloud.backend.service.dto.StudentTaskDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findAllByCourseId(Long courseId);

    void deleteById(Long id);

    @Query(value = "select t.id as id, t.task_name as taskName, c.course_name as courseName, t.from_time as fromTime, t.end_time as endTime, t.delay_time as delayTime from saas_tasks t, saas_courses c where t.course_id = c.id and c.id in (select s.course_id from saas_users_courses s where s.student_id = ?1)", nativeQuery = true)
    List<StudentTaskDTO> getAllTasksByStudentId(Long studentId);

    Optional<Task> findById(Long id);

    void deleteAllByCourseId(Long courseId);

}
