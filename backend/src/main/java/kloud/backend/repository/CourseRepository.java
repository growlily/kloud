package kloud.backend.repository;

import kloud.backend.entity.Course;
import kloud.backend.service.dto.StudentCourseDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByTeacherId(Long id);

    void deleteById(Long id);

//    @Cacheable(value = "courseRepositoryForTaskUseOnly", key = "#id")
    Optional<Course> findOneById(Long id);

    @Query(value = "select c.course_name as courseName, c.id as id from saas_courses c, saas_users_courses uc where c.id = uc.course_id and uc.student_id = ?1", nativeQuery = true)
    List<StudentCourseDTO> getCoursesByStudentId(Long id);

}
